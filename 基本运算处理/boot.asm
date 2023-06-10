    org 07c00h ; start at address 07c00h
    mov ax, cs ; initialize
    mov ds, ax
    mov es, ax
    call DispStr ; call the print method
    jmp $ ; exit code with endless loop
DispStr:
    mov ax, BootMessage
    mov bp, ax
    mov cx, 11
    mov ax, 01301h ; string-print mode and update cursor after print
    mov bx, 000ch ; present string with red color
    mov dl, 0 ; reset the cursor
    int 10h ; trap code
    ret ; exit function
BootMessage: db "hello world"
times 510-($-$$) db 0 ; fill other address with 0
dw 0xaa55 ; end with boot sector symbol
