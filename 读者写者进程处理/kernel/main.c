
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            main.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "protect.h"
#include "proto.h"
#include "string.h"
#include "global.h"

Semaphore  rmutex;
Semaphore  wmutex;
Semaphore  global_mutex;
Semaphore  rcount_mutex;
int allProcess[5] = {1, 1, 1, 1, 1};
int time_tick = 500;
/*======================================================================*
                            kernel_main
 *======================================================================*/
PUBLIC int kernel_main()
{
	disp_str("-----\"kernel_main\" begins-----\n");

	TASK*		p_task		= task_table;
	PROCESS*	p_proc		= proc_table;
	char*		p_task_stack	= task_stack + STACK_SIZE_TOTAL;
	u16		selector_ldt	= SELECTOR_LDT_FIRST;
	int i;
	for (i = 0; i < NR_TASKS; i++) {
		strcpy(p_proc->p_name, p_task->name);	// name of the process
		p_proc->pid = i;			// pid

		p_proc->ldt_sel = selector_ldt;

		memcpy(&p_proc->ldts[0], &gdt[SELECTOR_KERNEL_CS >> 3],
		       sizeof(DESCRIPTOR));
		p_proc->ldts[0].attr1 = DA_C | PRIVILEGE_TASK << 5;
		memcpy(&p_proc->ldts[1], &gdt[SELECTOR_KERNEL_DS >> 3],
		       sizeof(DESCRIPTOR));
		p_proc->ldts[1].attr1 = DA_DRW | PRIVILEGE_TASK << 5;
		p_proc->regs.cs	= ((8 * 0) & SA_RPL_MASK & SA_TI_MASK)
			| SA_TIL | RPL_TASK;
		p_proc->regs.ds	= ((8 * 1) & SA_RPL_MASK & SA_TI_MASK)
			| SA_TIL | RPL_TASK;
		p_proc->regs.es	= ((8 * 1) & SA_RPL_MASK & SA_TI_MASK)
			| SA_TIL | RPL_TASK;
		p_proc->regs.fs	= ((8 * 1) & SA_RPL_MASK & SA_TI_MASK)
			| SA_TIL | RPL_TASK;
		p_proc->regs.ss	= ((8 * 1) & SA_RPL_MASK & SA_TI_MASK)
			| SA_TIL | RPL_TASK;
		p_proc->regs.gs	= (SELECTOR_KERNEL_GS & SA_RPL_MASK)
			| RPL_TASK;

		p_proc->regs.eip = (u32)p_task->initial_eip;
		p_proc->regs.esp = (u32)p_task_stack;
		p_proc->regs.eflags = 0x1202; /* IF=1, IOPL=1 */

		p_task_stack -= p_task->stacksize;
		p_proc++;
		p_task++;
		selector_ldt += 1 << 3;
	}

	proc_table[0].ticks = proc_table[0].priority = 15;
	proc_table[1].ticks = proc_table[1].priority =  5;
	proc_table[2].ticks = proc_table[2].priority =  3;

	k_reenter = 0;
	ticks = 0;

	p_proc_ready	= proc_table;

        /* 初始化 8253 PIT */
        out_byte(TIMER_MODE, RATE_GENERATOR);
        out_byte(TIMER0, (u8) (TIMER_FREQ/HZ) );
        out_byte(TIMER0, (u8) ((TIMER_FREQ/HZ) >> 8));
        put_irq_handler(CLOCK_IRQ, clock_handler); /* 设定时钟中断处理程序 */
        enable_irq(CLOCK_IRQ);                     /* 让8259A可以接收时钟中断 */

	readerNum = 0;
		rmutex.count=3;
		rmutex.head=0;
		rmutex.tail=0;
		wmutex.count=1;
		wmutex.head=0;
		wmutex.tail=0;
		rcount_mutex.count=1;
		rcount_mutex.head=0;
		rcount_mutex.tail=0;
		global_mutex.count=1;
		global_mutex.head=0;
		global_mutex.tail=0;		
	clearScreen();
	restart();

	while(1){}
}

/*======================================================================*
                               clearScreen
 *======================================================================*/
void clearScreen(){
	u8* base=(u8*) 0xB8000;
	for (int i=0;i<0x8000;i=i+2){
		base[i]=' ';
	}
	disp_pos=0;
	lines=0;
}

/*======================================================================*
                               TestA
 *======================================================================*/
void TestA()
{
	int i = 0x0000;
	while(1){
		lines ++;
		char* num;
		if(lines >= 10){
			num = "xx\0";
			num[0] = (char)(0x30 + lines / 10);
			num[1] = (char)(0x30 + lines % 10);
			disp_str_interface(num);
		}
		else{
			num = "x\0";
			num[0] = (char)(0x30 + lines);
			disp_str_interface(num);
		}
		for(int i=0;i<5;i++){
			switch(allProcess[i]){
				case 0:
					disp_color_str(" Z", 0x01);
					break;
				case 1:
					disp_color_str(" X", 0x04);
					break;
				case 2:
					disp_color_str(" O", 0x02);
					break;
				default:
					disp_color_str(" ?", 0x07);
					break;
			}
		}
		// int cnt = (&global_mutex)->count;
		// char*s = "x\0";
		// s[0] = 0x30 + cnt;
		// disp_str_interface(s);
		disp_str_interface("\n");
		milli_delay(time_tick);
	}
}

/*======================================================================*
                               TestB(reader1)
 *======================================================================*/
void TestB()
{
	int rest_time = 1;
	int read_time = 2;
	while(1){
		p_interface(&global_mutex);
		p_interface(&rcount_mutex);
		if(readerNum == 0) p_interface(&wmutex);
		readerNum ++;
		v_interface(&rcount_mutex);
		p_interface(&rmutex);
		v_interface(&global_mutex);
		allProcess[0] = 2;
		milli_delay(read_time*time_tick);
		v_interface(&rmutex);
		p_interface(&rcount_mutex);
		if(readerNum == 1) v_interface(&wmutex);
		readerNum --;
		v_interface(&rcount_mutex);
		allProcess[0] = 0;
		milli_delay(rest_time*time_tick);
		allProcess[0] = 1;
	}
}

/*======================================================================*
                               TestC(reader2)
 *======================================================================*/
void TestC()
{
	int rest_time = 1;
	int read_time = 3;
	while(1){
		p_interface(&global_mutex);
		p_interface(&rcount_mutex);
		if(readerNum == 0) p_interface(&wmutex);
		readerNum ++;
		v_interface(&rcount_mutex);
		p_interface(&rmutex);
		v_interface(&global_mutex);
		allProcess[1] = 2;
		milli_delay(read_time*time_tick);
		v_interface(&rmutex);
		p_interface(&rcount_mutex);
		if(readerNum == 1) v_interface(&wmutex);
		readerNum --;
		v_interface(&rcount_mutex);
		allProcess[1] = 0;
		milli_delay(rest_time*time_tick);
		allProcess[1] = 1;
	}
}

/*======================================================================*
                               TestD(reader3)
 *======================================================================*/
void TestD()
{
	int rest_time = 1;
	int read_time = 3;
	while(1){
		p_interface(&global_mutex);
		p_interface(&rcount_mutex);
		if(readerNum == 0) p_interface(&wmutex);
		readerNum ++;
		v_interface(&rcount_mutex);
		p_interface(&rmutex);
		v_interface(&global_mutex);
		allProcess[2] = 2;
		milli_delay(read_time*time_tick);
		v_interface(&rmutex);
		p_interface(&rcount_mutex);
		if(readerNum == 1) v_interface(&wmutex);
		readerNum --;
		v_interface(&rcount_mutex);
		allProcess[2] = 0;
		milli_delay(rest_time*time_tick);
		allProcess[2] = 1;
	}
}

/*======================================================================*
                               TestE(writer1)
 *======================================================================*/
void TestE()
{
	int rest_time = 1;
	int write_time = 3;
	while(1){
		p_interface(&global_mutex);
		p_interface(&wmutex);
		v_interface(&global_mutex);
		allProcess[3] = 2;
		milli_delay(write_time*time_tick);
		v_interface(&wmutex);
		allProcess[3] = 0;
		milli_delay(rest_time*time_tick);
		allProcess[3] = 1;
	}
}

/*======================================================================*
                               TestF(writer2)
 *======================================================================*/
void TestF()
{
	int rest_time = 1;
	int write_time = 4;
	while(1){
		p_interface(&global_mutex);
		p_interface(&wmutex);
		v_interface(&global_mutex);
		allProcess[4] = 2;
		milli_delay(write_time*time_tick);
		v_interface(&wmutex);
		allProcess[4] = 0;
		milli_delay(rest_time*time_tick);
		allProcess[4] = 1;
	}
}