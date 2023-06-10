; calculate
section .data
errorInfo: db "Input Error!"
negative: db '-'
zero: db "0"
nextLine: db 0Ah

section .bss
; save two input ascii numbers
str1: resb 21
str2: resb 21
; save the operands
op1: resb 42
op2: resb 42
; save the operands when executing multiple calculation
mult1: resb 21
mult2: resb 21
; save the operator
operator: resb 1
; save the length of two numbers
length1: resd 1
length2: resd 1

currentChar: resb 1
errorFlag: resd 1
endFlag: resd 1
; save the sign of two numbers
sign1: resb 1
sign2: resb 1
; save the result address and length
resAddr: resd 1
resLength: resd 1
; flags when calculating
cf: resb 1
sf: resb 1
zf: resb 1
; temperary varieties
temp1: resd 1
temp2: resd 1
; save the value of registers temperarily
eaxVal: resd 1
ebxVal: resd 1
ecxVal: resd 1
edxVal: resd 1
; temperary varieties when executing multiple calculation
multTemp1: resd 1
multTemp2: resd 1
multResAddr: resd 1

section .text

global _start
_start:
loop:
    call initialize
    call getInput
    cmp dword[endFlag], 0 ; when input 'q', exit the loop
    jnz endCalculate
    cmp dword[errorFlag], 0 ; if error, then end the current loop and receive next input
    jnz loop
    call str2num
    call startCalculate
    call printStr
    jmp loop
endCalculate:
    call exit


; print '\n'
; in:
; out:
printNextLine:
    ; save the value of the registers
    mov dword[eaxVal], eax
    mov dword[ebxVal], ebx
    mov dword[ecxVal], ecx
    mov dword[edxVal], edx
    mov eax, 4
    mov ebx, 1
    mov ecx, nextLine
    mov edx, 1
    int 80h
    mov eax, dword[eaxVal]
    mov ebx, dword[ebxVal]
    mov ecx, dword[ecxVal]
    mov edx, dword[edxVal]
    ret

; initial the parameter
; in: 
; out: 
initialize:
    mov dword[length1], 0
    mov dword[length2], 0
    mov dword[errorFlag], 0
    mov dword[endFlag], 0
    mov byte[sign1], 0
    mov byte[sign2], 0
    mov byte[cf], 0
    mov byte[sf], 0
    mov byte[zf], 0
    ret

; get input
; in: 
; out: 
getInput:
getFirstInput:
    mov eax, 3 ; call sys_read function
    mov ebx, 0 ; stdin
    mov ecx, currentChar
    mov edx, 1 ; read one char each time
    int 80h
    cmp byte[currentChar], 0Ah ; if the first char is '\n', continue to receive next char
    je getFirstInput
getFirstInputLoop1:
    ; check if the input is legal
    cmp byte[currentChar], 'q'
    jne notExit
    inc dword[endFlag]
    ret
notExit:
    cmp byte[currentChar], '+'
    je getSecondInput
    cmp byte[currentChar], '*'
    je getSecondInput
    cmp byte[currentChar], '-'
    jne getFirstInputContinue1
    cmp dword[length1], 0 ; if '-' doesn't appear at the beginning, then print error info
    jne inputError
    inc byte[sign1]
    jmp getFirstInputContinue2
getFirstInputContinue1:
    cmp byte[currentChar], '0'
    js inputError
    cmp byte[currentChar], ':'
    jns inputError
    ; append the char to string1
    cmp dword[length1], 21 ; if the number is too large, the print error info
    jns inputError
    mov eax, str1
    add eax, dword[length1]
    mov bl, byte[currentChar]
    mov byte[eax], bl
    inc dword[length1]
getFirstInputContinue2:
    mov eax, 3 ; call sys_read function
    mov ebx, 0 ; stdin
    mov ecx, currentChar
    mov edx, 1 ; read one char each time
    int 80h
    jmp getFirstInputLoop1
getSecondInput:
    ; if length1 = 0, then print error info
    cmp dword[length1], 0
    je inputError
    ; save the operator
    mov bl, byte[currentChar]
    mov byte[operator], bl
getSecondInputloop:
    mov eax, 3
    mov ebx, 0
    mov ecx, currentChar
    mov edx, 1
    int 80h
    ; if currentChar is '-' and is at the beginning, then set the sign as 1
    cmp byte[currentChar], '-'
    jne continue2
    cmp dword[length2], 0
    jne inputError
    inc byte[sign2]
    jmp getSecondInputloop
continue2:
    cmp byte[currentChar], 0Ah ; when read 0ah, finish reading
    jne notNextLine
    cmp dword[length2], 0
    jz inputError
    ret
notNextLine:
    ; if currentChar isn't a digit, then print error info
    cmp byte[currentChar], '0'
    js inputError
    cmp byte[currentChar], ':'
    jns inputError
    cmp dword[length2], 21 ; if the number is too large, the print error info
    jns inputError
    ; append char to str2
    mov eax, str2
    add eax, dword[length2]
    mov bl, byte[currentChar]
    mov byte[eax], bl
    inc dword[length2]
    jmp getSecondInputloop

; print the error info after input illegally
; in:
; out:
inputError:
    ; read the left chars until read '\n'
inputErrorLoop1:
    cmp byte[currentChar], 0Ah
    je inputErrorContinue
    mov eax, 3
    mov ebx, 0
    mov ecx, currentChar
    mov edx, 1
    int 80h
    jmp inputErrorLoop1
inputErrorContinue:
    mov eax, 4 ; call sys_write function
    mov ebx, 1 ; stdout
    mov ecx, errorInfo
    mov edx, 12
    int 80h
    call printNextLine ; print '\n'
    inc dword[errorFlag]
    ret

; print the result
; in: dword[resAddr], dword[resLength]
; out:
printStr:
    cmp byte[zf], 0 ; if the result is zero, then print 0
    jz printNotZero
    mov eax, 4
    mov ebx, 1
    mov ecx, zero
    mov edx, 1
    int 80h
    call printNextLine ; print '\n'
    ret
printNotZero:
    ; if the result is negative, then print '-'
    cmp byte[sf], 0
    jz printContinue
    mov eax, 4
    mov ebx, 1
    mov ecx, negative
    mov edx, 1
    int 80h
printContinue:
    mov ecx, dword[resLength]
    mov eax, op1
    add eax, ecx
printLoop1:
    dec eax
    dec ecx
    cmp byte[eax], 0
    jz printLoop1
printLoop2:
    add byte[eax], 30h
    ; save the value temperarily
    mov dword[temp1], eax
    mov dword[temp2], ecx
    mov eax, 4
    mov ebx, 1
    mov ecx, dword[temp1]
    mov edx, 1
    int 80h
    mov eax, dword[temp1]
    mov ecx, dword[temp2]
    dec eax
    dec ecx
    cmp ecx, 0
    jns printLoop2
    call printNextLine ; print '\n'
    ret

; call sys_exit to exit the program
; in:
; out:
exit:
    mov eax, 1
    mov ebx, 0
    int 80h
    ret

; turn string into integer
; in: str1, str2
; out: op1, op2 = string after convert
str2num:
    ; copy str1 to op1
    mov ecx, 0
    mov eax, str1
    add eax, dword[length1]
    dec eax
    mov ebx, op1
str2numLoop1:
    mov dl, byte[eax]
    sub dl, 30h ; sub every char with 30h to convert ascii char to number
    mov byte[ebx], dl
    inc ebx
    dec eax
    inc ecx
    cmp ecx, dword[length1]
    js str2numLoop1
str2numLoop2:
    cmp ecx, 42
    jns str2numContinue
    mov byte[ebx], 0
    inc ebx
    inc ecx
    jmp str2numLoop2
str2numContinue:
    ; copy str2 to op2
    mov ecx, 0
    mov eax, str2
    add eax, dword[length2]
    dec eax
    mov ebx, op2
str2numLoop3:
    mov dl, byte[eax]
    sub dl, 30h
    mov byte[ebx], dl
    inc ebx
    dec eax
    inc ecx
    cmp ecx, dword[length2]
    js str2numLoop3
str2numLoop4:
    cmp ecx, 42
    jns str2numEnd
    mov byte[ebx], 0
    inc ebx
    inc ecx
    jmp str2numLoop4
str2numEnd:
    ret

; set the sign of the result and start the calculation
; in:
; out: sf = the sign of the result
startCalculate:
    ; check the operator
    cmp byte[operator], '+'
    je startCalculateAdd
    cmp byte[operator], '*'
    jne inputError
    mov dl, byte[sign1]
    cmp dl, byte[sign2] ; if two numbers have the same sign, then set the result sign as '+'
    je startCalculateContinue
    inc byte[sf]
startCalculateContinue:
    mov eax, op1
    mov dword[resAddr], eax
    mov dword[resLength], 42
    call multipleFunc
    ret
startCalculateAdd:
    ; if two numbers have the same sign, then execute add calculation
    mov dl, byte[sign1]
    mov byte[sf], dl
    cmp dl, byte[sign2]
    mov eax, op1
    mov dword[resAddr], eax
    mov dword[resLength], 21
    je startCalculateSameSign
    call subFunc
    ret 
startCalculateSameSign:
    ; execute sub calculation
    call addFunc
    ret

; add two unsigned numbers
; in: op1, op2, resLength
; out: op1 = op1 + op2
addFunc:
    ; save the value in the registers
    mov dword[eaxVal], eax
    mov dword[ebxVal], ebx
    mov dword[ecxVal], ecx
    mov dword[edxVal], edx
    mov eax, op1
    mov ecx, 0
; check if op1 and op2 = 0
addFuncCheckZero1:
    cmp byte[eax], 0
    jnz addFuncNotZero
    inc eax
    inc ecx
    cmp ecx, 21
    js addFuncCheckZero1
    mov eax, op2
    mov ecx, 0
addFuncCheckZero2:
    cmp byte[eax], 0
    jnz addFuncNotZero
    inc eax
    inc ecx
    cmp ecx, 21
    js addFuncCheckZero2
    inc byte[zf]
    ret
addFuncNotZero:
    mov byte[cf], 0
    mov ecx, 0
    mov eax, op1
    mov ebx, op2
addFuncLoop:
    ; add every level
    mov dl, byte[eax]
    add dl, byte[ebx]
    add dl, byte[cf]
    mov byte[cf], 0
    cmp dl, 0Ah ; check the carry
    js addFuncNoCarry
    inc byte[cf]
    sub dl, 0Ah
addFuncNoCarry:
    mov byte[eax], dl
    inc ecx
    inc eax
    inc ebx
    cmp ecx, dword[resLength]
    js addFuncLoop
    mov eax, dword[eaxVal]
    mov ebx, dword[ebxVal]
    mov ecx, dword[ecxVal]
    mov edx, dword[edxVal]
    ret

; sub two unsigned numbers
; in: op1, op2
; out: op1 = op1 - op2
subFunc:
    mov eax, op1
    mov ebx, op2
    mov ecx, 21
    add eax, ecx
    add ebx, ecx
subFuncCompare:
    ; compare op1 and op2
    cmp ecx, 1
    js subFuncEqual
    dec eax
    dec ebx
    dec ecx
    mov dl, byte[eax]
    cmp dl, byte[ebx]
    je subFuncCompare
    jns subFuncContinue1
    ; if op1 < op2 then change the signFlag and swap op1 and op2
    mov dh, 1
    sub dh, byte[sf]
    mov byte[sf], dh
    mov eax, op1
    mov ebx, op2
    mov ecx, 0
subFuncSwap:
    mov dl, byte[eax] ; swap every byte
    mov dh, byte[ebx]
    mov byte[ebx], dl
    mov byte[eax], dh
    inc eax
    inc ebx
    inc ecx
    cmp ecx, 21
    js subFuncSwap
subFuncContinue1:
    ; do sub function
    mov byte[cf], 0
    mov eax, op1
    mov ebx, op2
    mov ecx, 0
subFuncLoop:
    mov dl, byte[ebx]
    add dl, byte[cf]
    mov byte[cf], 0
    cmp byte[eax], dl
    jns subFuncContinue2
    ; if [eax] < [ebx] + [cf], then borrow from high level
    inc byte[cf]
    add byte[eax], 0Ah
subFuncContinue2:
    sub byte[eax], dl
    inc eax
    inc ebx
    inc ecx
    cmp ecx, 21
    js subFuncLoop
    ret
subFuncEqual:
    inc byte[zf]
    ret

; multiple two unsigned numbers
; in: op1, op2
; out: op1 = op1 * op2
multipleFunc:
    mov dword[eaxVal], eax
    mov dword[ebxVal], ebx
    mov dword[ecxVal], ecx
    mov dword[edxVal], edx
    ; if one operand is zero then set the result zero
    mov eax, op1
    mov ecx, 0
multipleFuncCheakZero1:
    cmp byte[eax], 0
    jnz multipleFuncNotZero1
    inc ecx
    inc eax
    cmp ecx, 21
    js multipleFuncCheakZero1
    inc byte[zf]
    ret
multipleFuncNotZero1:
    mov eax, op2
    mov ecx, 0
multipleFuncCheakZero2:
    cmp byte[eax], 0
    jnz multipleFuncNotZero2
    inc ecx
    inc eax
    cmp ecx, 21
    js multipleFuncCheakZero2
    inc byte[zf]
    ret
multipleFuncNotZero2:
    ; clear mult1 and mult2
    mov eax, mult1
    mov ecx, 0
multipleFuncClearMult1:
    mov byte[eax], 0
    inc eax
    inc ecx
    cmp ecx, 21
    js multipleFuncClearMult1
    mov eax, mult2
    mov ecx, 0
multipleFuncClearMult2:
    mov byte[eax], 0
    inc eax
    inc ecx
    cmp ecx, 21
    js multipleFuncClearMult2
    ; copy op1 and op2 to mult1 and mult2
    mov eax, op1
    mov ebx, mult1
    mov ecx, 0
multipleFuncCopy1:
    mov dl, byte[eax]
    mov byte[ebx], dl
    inc eax
    inc ebx
    inc ecx
    cmp ecx, dword[length1]
    js multipleFuncCopy1
    mov eax, op2
    mov ebx, mult2
    mov ecx, 0
multipleFuncCopy2:
    mov dl, byte[eax]
    mov byte[ebx], dl
    inc eax
    inc ebx
    inc ecx
    cmp ecx, dword[length2]
    js multipleFuncCopy2
    ; clear op1 to save the result
    mov eax, op1
    mov ecx, 0
multipleFuncClear:
    mov byte[eax], 0
    inc eax
    inc ecx
    cmp ecx, 42
    js multipleFuncClear
    ; start calculation
    mov byte[cf], 0 ; clear the carryFlag
    mov ebx, mult2
    mov dword[multTemp1], 0 ; set the loop1Count
multipleFuncLoop1:
    ; clear op2 every loop
    mov eax, op2
    mov ecx, 0
multipleFuncResetOp2:
    mov byte[eax], 0
    inc eax
    inc ecx
    cmp ecx, 42
    js multipleFuncResetOp2
    ; set the offset levels
    mov ecx, dword[multTemp1]
    mov dword[multResAddr], op2
    add dword[multResAddr], ecx
    ; multiple mult1 with one level in mult2
multipleFuncContinue1:
    mov edx, mult1
    mov dword[multTemp2], 0
multipleFuncLoop2:
    mov eax, 0
    mov al, byte[ebx]
    mul byte[edx]
    add al, byte[cf]
    mov byte[cf], 0 ; reset [cf] after calculation
multipleFuncSetCarry:
    ; check the carry
    cmp al, 0Ah ; if al > 10, then [cf] + 1 and al - 10
    js multipleFuncContinue2
    sub al, 0Ah
    inc byte[cf]
    jmp multipleFuncSetCarry
multipleFuncContinue2:
    ; add the result to op1
    mov dword[edxVal], edx
    mov edx, dword[multResAddr]
    mov byte[edx], al
    mov edx, dword[edxVal]
    inc edx
    inc dword[multResAddr]
    inc dword[multTemp2]
    mov eax, dword[multTemp2]
    cmp eax, 21
    js multipleFuncLoop2 ; loop2 end
    call addFunc ; add every result together
    inc ebx
    inc dword[multTemp1]
    mov ecx, dword[multTemp1]
    cmp ecx, 21
    js multipleFuncLoop1 ; loop1 end
    mov eax, dword[eaxVal]
    mov ebx, dword[ebxVal]
    mov ecx, dword[ecxVal]
    mov edx, dword[edxVal]
    ret

