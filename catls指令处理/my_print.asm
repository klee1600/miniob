global printRed
global printWhite

section .data
color_red	db 1Bh, '[31;1m', 0
.len		equ $-color_red
color_white	db 1Bh, '[37;0m', 0
.len		equ $-color_white

section .text

printRed:
    ; set the color
    mov eax, 4
    mov ebx, 1
    mov ecx, color_red
    mov edx, color_red.len
    int 80h
    ; print 
    mov eax, 4
    mov ebx, 1
    mov ecx, [esp+4]
    mov edx, [esp+8]
    int 80h
    ret


printWhite:
    ; set the color
    mov eax, 4
    mov ebx, 1
    mov ecx, color_white
    mov edx, color_white.len
    int 80h
    ; print 
    mov eax, 4
    mov ebx, 1
    mov ecx, [esp+4]
    mov edx, [esp+8]
    int 80h
    ret
