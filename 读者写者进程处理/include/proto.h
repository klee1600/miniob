
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            proto.h
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "proc.h"

/* klib.asm */
PUBLIC void	out_byte(u16 port, u8 value);
PUBLIC u8	in_byte(u16 port);
PUBLIC void	disp_str(char * info);
PUBLIC void	disp_color_str(char * info, int color);

/* protect.c */
PUBLIC void	init_prot();
PUBLIC u32	seg2phys(u16 seg);

/* klib.c */
PUBLIC void	delay(int time);

/* kernel.asm */
void restart();

/* main.c */
// main.c中的函数声明
void TestA();
void TestB();
void TestC();
void TestD();
void TestE();
void TestF();
void clearScreen();

/* i8259.c */
PUBLIC void put_irq_handler(int irq, irq_handler handler);
PUBLIC void spurious_irq(int irq);

/* clock.c */
PUBLIC void clock_handler(int irq);


/* 以下是系统调用相关 */

/* proc.c */
PUBLIC  int     sys_get_ticks();        /* sys_call */

/* syscall.asm */
PUBLIC  void    sys_call();             /* int_handler */
PUBLIC  int     get_ticks();

// syscall.asm文件新增函数声明
PUBLIC void     sys_disp_str(char *);
PUBLIC void     disp_str_interface(char *);
PUBLIC void     sys_delay(int);
PUBLIC void     delay_interface(int);
PUBLIC void     sys_p(Semaphore *);
PUBLIC void     sys_v(Semaphore *);
PUBLIC void     p_interface(Semaphore *);
PUBLIC void     v_interface(Semaphore *);

