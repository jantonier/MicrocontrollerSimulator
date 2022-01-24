  ORG 0
 JMPADDR start
x db 10
start:
//KEYBOARD VA EN DD (221)
 LOAD R1, #DD
 LOAD R2, #DD
 LOAD R3, #DD
 ADD R4, R1, R2
 ADD R5, R4, R3
fin:
 JMPADDR fin 





