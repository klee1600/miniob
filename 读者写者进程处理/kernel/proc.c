
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                               proc.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "protect.h"
#include "proto.h"
#include "string.h"
#include "global.h"

/*======================================================================*
                              schedule
 *======================================================================*/
PUBLIC void schedule()
{
	PROCESS* p;
	// 更改查询方式
	int current_tick = get_ticks();
	while (1) {
		p_proc_ready++;
		if (p_proc_ready >= proc_table + NR_TASKS) {
			p_proc_ready = proc_table;
		}
		if (p_proc_ready->semaphore == 0 &&
		p_proc_ready->start_time <= current_tick) {
			break; // 寻找到进程
		}
	}
}

/*======================================================================*
                           sys_get_ticks
 *======================================================================*/
PUBLIC int sys_get_ticks()
{
	return ticks;
}

/*======================================================================*
                           sys_delay
 *======================================================================*/
PUBLIC void sys_delay(int time){
	p_proc_ready->start_time=get_ticks()+time/(1000/HZ);
	schedule();
}


/*======================================================================*
                           sys_p
 *======================================================================*/
PUBLIC void sys_p(Semaphore* s){
	s->count--;
	if (s->count<0){
		p_proc_ready->semaphore=s;
		s->queue[s->tail]=p_proc_ready;
		s->tail++;
		schedule();
	}
};

/*======================================================================*
                           sys_v
 *======================================================================*/
PUBLIC void sys_v(Semaphore* s){
	s->count++;
	if (s->count<=0){
		PROCESS* p = s->queue[s->head];
		p->semaphore=0;
		s->head++;
	}
};