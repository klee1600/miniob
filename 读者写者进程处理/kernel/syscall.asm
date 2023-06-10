
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;                               syscall.asm
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;                                                     Forrest Yu, 2005
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

%include "sconst.inc"

_NR_get_ticks       equ 0 ; 要跟 global.c 中 sys_call_table 的定义相对应！
_NR_disp_str_interface      equ 1
_NR_delay_interface        equ 2
_NR_p_interface		     equ 3
_NR_v_interface		     equ 4
INT_VECTOR_SYS_CALL equ 0x90
extern  disp_str

; 导出符号
global	get_ticks
global	disp_str_interface
global  delay_interface
global	sys_disp_str
global  p_interface
global  v_interface


bits 32
[section .text]

; ====================================================================
;                              get_ticks
; ====================================================================
get_ticks:
	mov	eax, _NR_get_ticks
	int	INT_VECTOR_SYS_CALL
	ret

; 各个系统调用的汇编实现
; ====================================================================
;                             sys_disp_str
; ====================================================================
sys_disp_str:
	pusha
	push ebx
	call disp_str
	popa
	pop ebx
	ret

; ====================================================================
;                             disp_str_interface
; ====================================================================
disp_str_interface:
	mov eax, _NR_disp_str_interface
	push ebx
	mov ebx, [esp + 8]
	int INT_VECTOR_SYS_CALL
	pop ebx
	ret

; ====================================================================
;                             	delay_interface
; ====================================================================
delay_interface:
	mov eax,_NR_delay_interface
	push ebx
	mov ebx,[esp + 8]
	int INT_VECTOR_SYS_CALL
	pop ebx
	ret


; ====================================================================
;                             	p_interface
; ====================================================================
p_interface:
	mov eax,_NR_p_interface
	push ebx
	mov ebx,[esp + 8]
	int INT_VECTOR_SYS_CALL
	pop ebx
	ret


; ====================================================================
;                             	v_interface
; ====================================================================
v_interface:
	mov eax,_NR_v_interface
	push ebx
	mov ebx,[esp + 8]
	int INT_VECTOR_SYS_CALL
	pop ebx
	ret