
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			      console.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

/*
	回车键: 把光标移到第一列
	换行键: 把光标前进到下一行
	Tab键: 打印四个空格
	Ctrl+z: 撤回上一个操作
	Esc键: 进入查找模式（再次按下后退出）
*/


#include "type.h"
#include "const.h"
#include "protect.h"
#include "string.h"
#include "proc.h"
#include "tty.h"
#include "console.h"
#include "global.h"
#include "keyboard.h"
#include "proto.h"

PRIVATE void set_cursor(unsigned int position);
PRIVATE void set_video_start_addr(u32 addr);
PRIVATE void flush(CONSOLE* p_con);

/*======================================================================*
			   init_screen
 *======================================================================*/
PUBLIC void init_screen(TTY* p_tty)
{
	int nr_tty = p_tty - tty_table;
	p_tty->p_console = console_table + nr_tty;

	int v_mem_size = V_MEM_SIZE >> 1;	/* 显存总大小 (in WORD) */

	int con_v_mem_size                   = v_mem_size / NR_CONSOLES;
	p_tty->p_console->original_addr      = nr_tty * con_v_mem_size;
	p_tty->p_console->v_mem_limit        = con_v_mem_size;
	p_tty->p_console->current_start_addr = p_tty->p_console->original_addr;

	/* 默认光标位置在最开始处 */
	p_tty->p_console->cursor = p_tty->p_console->original_addr;

	if (nr_tty == 0) {
		/* 第一个控制台沿用原来的光标位置 */
		p_tty->p_console->cursor = disp_pos / 2;
		disp_pos = 0;
	}
	else {
		out_char(p_tty->p_console, nr_tty + '0');
		out_char(p_tty->p_console, '#');
	}
	clear_screen(p_tty->p_console);
}


/*======================================================================*
			   is_current_console
*======================================================================*/
PUBLIC int is_current_console(CONSOLE* p_con)
{
	return (p_con == &console_table[nr_current_console]);
}


/*======================================================================*
			   out_char
 *======================================================================*/
PUBLIC void out_char(CONSOLE* p_con, char ch)
{
	u8* p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);
	// not receive any input when search mode + '\n'
	if(search_state == 2 && ch != '\r') return;
	switch(ch) {
	case '\n':
	// normal input mode
		if(search_state == 0){
			if (p_con->cursor < p_con->original_addr +
		    p_con->v_mem_limit - SCREEN_WIDTH) {
				line_end_pos[current_line] = p_con->cursor;
				current_line++;
				p_con->cursor = p_con->original_addr + SCREEN_WIDTH * 
				((p_con->cursor - p_con->original_addr) /
				 SCREEN_WIDTH + 1);
				op_point ++;
				op_list[op_point] = 1;
				p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2 - 2);
				*p_vmem = '\n';
				*(p_vmem + 1) = BLACK;
			}
		}
		// search mode
		else if(search_state == 1){
			// start search
			char target_string[1000];
			int target_length = 0;
			for(int i=1;i<=(p_con->cursor-search_start_pos);i++){
				target_string[i-1] = *(p_vmem - 2*(p_con->cursor-search_start_pos) + 2*target_length);
				target_length ++;
			}
			int offset = p_con->cursor - p_con->original_addr;
			for(int i=p_con->original_addr;i<search_start_pos-target_length+1;i++){
				int istarget = 1;
				for(int j=0;j<target_length;j++){
					if(*(p_vmem-2*(offset-j)) != target_string[j]){
						istarget = 0;
						break;
					}
				}
				if(istarget){
					for(int j=0;j<target_length;j++){
						if(*(p_vmem-2*(offset-j))=='\0' && *(p_vmem-2*(offset-j)+1)==BLACK) continue;
						*(p_vmem-2*(offset-j)+1) = RED;
					}
				}
				offset --;
			}
			search_state = 2;
		}
		break;
	case '\b':
	// back
		if (p_con->cursor == p_con->original_addr || (search_state == 1 && p_con->cursor == search_start_pos)){
			break;
		}
		// delete '\n'
		if ((p_con->cursor - p_con->original_addr)%SCREEN_WIDTH==0 && *(p_vmem-2)=='\n'){
			p_con->cursor=line_end_pos[--current_line];
			op_point ++;
			op_list[op_point] = 0;
			deleted_char[op_point] = '\n';
		}
		// delete '\t'
		else if (*(p_vmem-8) == '\0' && *(p_vmem-7) == BLACK && *(p_vmem-6) == ' ' && *(p_vmem-4) == ' ' && *(p_vmem-2) == ' '){
			p_con->cursor -= 4;
			op_point ++;
			op_list[op_point] = 0;
			deleted_char[op_point] = '\t';
			for(int i=1;i<=4;i++){
				*(p_vmem-2*i) = ' ';
				*(p_vmem-2*i+1) = DEFAULT_CHAR_COLOR;
			}
		}
		// delete other chars
		else if (p_con->cursor > p_con->original_addr) {
			op_point ++;
			op_list[op_point] == 0;
			deleted_char[op_point] = *(p_vmem-2);
			p_con->cursor--;
			*(p_vmem-2) = ' ';
			*(p_vmem-1) = DEFAULT_CHAR_COLOR;
		}

		break;
	case '\a':
	// withdraw
		if ((search_state == 1 && op_point == search_start_point) || (search_state == 0 && op_point == 0)) {
			break;
		}
		int latest_op = op_list[op_point];
		// if latest operation is deletion...
		if(latest_op == 0){
			char last_char = deleted_char[op_point];
			// if deleted char is '\n'...
			// char ascii[2] = {'0', '0'};
			// if(last_char >= 10){
			// 	ascii[0] = (char)(last_char/10 + 0x30);
			// }
			// ascii[1] = (char)(last_char%10 + 0x30);
			// disp_str(ascii);
			if(last_char == '\n'){
				line_end_pos[current_line++] = p_con->cursor;
				p_con->cursor = p_con->original_addr + SCREEN_WIDTH * 
				((p_con->cursor - p_con->original_addr) /
				 SCREEN_WIDTH + 1);
				p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2 - 2);
				*p_vmem = '\n';
				*(p_vmem + 1) = BLACK;
			}
			// if deleted char is '\t'...
			else if(last_char == '\t'){
				*p_vmem++ = '\0';
				*p_vmem++ = BLACK;
				p_con->cursor ++;
				for (int i=1;i<=3;i++){
					*p_vmem++ = ' ';
					*p_vmem++ = DEFAULT_CHAR_COLOR;
					p_con->cursor++;
				}
			}
			// other chars...
			else{
				*(p_vmem++) = last_char;
				if (search_state==0){
					*p_vmem ++ = DEFAULT_CHAR_COLOR;
				}else if (search_state==1){
					*p_vmem ++ = RED;
				}
				p_con->cursor ++;
			}
		}
		// if latest operation is insertion...
		else{
			if ((p_con->cursor - p_con->original_addr)%SCREEN_WIDTH==0 && *(p_vmem-2)=='\n'){
				p_con->cursor=line_end_pos[--current_line];
			}
			else if (*(p_vmem-8) == '\0' && *(p_vmem-7) == BLACK && *(p_vmem-6) == ' ' && *(p_vmem-4) == ' ' && *(p_vmem-2) == ' '){
				p_con->cursor -= 4;
				for(int i=1;i<=4;i++){
					*(p_vmem-2*i) = ' ';
					*(p_vmem-2*i+1) = DEFAULT_CHAR_COLOR;
				}
			}
			else{
				p_con->cursor--;
				*(p_vmem-2) = ' ';
				*(p_vmem-1) = DEFAULT_CHAR_COLOR;
			}
		}
		op_point--;
		break;
	case '\r':
	//search
		if(search_state >= 1) search_state = 0;
		else search_state = 1;
		if(search_state == 0){
			// back to the normal state
			int offset = 2;
			for(; offset<=2*(p_con->cursor-p_con->original_addr); offset+=2){
				if(*(p_vmem-offset)!='\0') *(p_vmem-offset+1) = DEFAULT_CHAR_COLOR;
			}
			offset = 2;
			for(; p_con->cursor>search_start_pos; p_con->cursor--){
				*(p_vmem-offset) = ' ';
				*(p_vmem-offset+1) = DEFAULT_CHAR_COLOR;
				offset += 2;
			}
			op_point = search_start_point;
			flush(p_con);
		}
		if(search_state == 1){
			// get into the search mode
			search_start_point = op_point;
			search_start_pos = p_con->cursor;
		}
		break;
	case '\t':
	// tab
		*p_vmem++ = '\0';
		*p_vmem++ = BLACK;
		p_con->cursor++;
		for (int i=1;i<=3;i++){
			*p_vmem++ = ' ';
			*p_vmem++ = DEFAULT_CHAR_COLOR;
			p_con->cursor++;
		}
		op_list[++op_point] = 1;
		break;
	default:
		if (p_con->cursor <
		    p_con->original_addr + p_con->v_mem_limit - 1) {
			*p_vmem++ = ch;
			if (search_state==0){
				*p_vmem++ = DEFAULT_CHAR_COLOR;
			}else if (search_state==1){
				*p_vmem++ = RED;
			}
			op_list[++op_point] = 1;
			p_con->cursor++;
		}
		break;
	}

	while (p_con->cursor >= p_con->current_start_addr + SCREEN_SIZE) {
		scroll_screen(p_con, SCR_DN);
	}

	flush(p_con);
}

/*======================================================================*
                           flush
*======================================================================*/
PRIVATE void flush(CONSOLE* p_con)
{
        set_cursor(p_con->cursor);
        set_video_start_addr(p_con->current_start_addr);
}

/*======================================================================*
			    set_cursor
 *======================================================================*/
PRIVATE void set_cursor(unsigned int position)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, CURSOR_H);
	out_byte(CRTC_DATA_REG, (position >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, CURSOR_L);
	out_byte(CRTC_DATA_REG, position & 0xFF);
	enable_int();
}

/*======================================================================*
			  set_video_start_addr
 *======================================================================*/
PRIVATE void set_video_start_addr(u32 addr)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, START_ADDR_H);
	out_byte(CRTC_DATA_REG, (addr >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, START_ADDR_L);
	out_byte(CRTC_DATA_REG, addr & 0xFF);
	enable_int();
}



/*======================================================================*
			   select_console
 *======================================================================*/
PUBLIC void select_console(int nr_console)	/* 0 \0 (NR_CONSOLES - 1) */
{
	if ((nr_console < 0) || (nr_console >= NR_CONSOLES)) {
		return;
	}

	nr_current_console = nr_console;

	set_cursor(console_table[nr_console].cursor);
	set_video_start_addr(console_table[nr_console].current_start_addr);
}

/*======================================================================*
			   scroll_screen
 *----------------------------------------------------------------------*
 滚屏.
 *----------------------------------------------------------------------*
 direction:
	SCR_UP	: 向上滚屏
	SCR_DN	: 向下滚屏
	其它	: 不做处理
 *======================================================================*/
PUBLIC void scroll_screen(CONSOLE* p_con, int direction)
{
	if (direction == SCR_UP) {
		if (p_con->current_start_addr > p_con->original_addr) {
			p_con->current_start_addr -= SCREEN_WIDTH;
		}
	}
	else if (direction == SCR_DN) {
		if (p_con->current_start_addr + SCREEN_SIZE <
		    p_con->original_addr + p_con->v_mem_limit) {
			p_con->current_start_addr += SCREEN_WIDTH;
		}
	}
	else{
	}

	set_video_start_addr(p_con->current_start_addr);
	set_cursor(p_con->cursor);
}

/*======================================================================*
			   clear_screen
 *----------------------------------------------------------------------*
 清屏.
 *======================================================================*/
PUBLIC void clear_screen(CONSOLE* p_con)
{
	u8* p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);
	int offset = 2;
	for(; p_con->cursor > p_con->original_addr; p_con->cursor--){
		*(p_vmem - offset) = ' ';
		*(p_vmem - offset + 1) = DEFAULT_CHAR_COLOR;
		offset += 2;
	}
	flush(p_con);
	search_state = 0;
	op_point = 0;
	current_line = 0;
}
