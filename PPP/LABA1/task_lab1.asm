;***** �������� �� ����������� ������ �1 (����� EV8031 / AVR) *****
;*** �������� � ������ ��������� ***

;���������� �����, �� ������ ���� ������� � ����� ��� ATmega8515
.include "m8515def.inc"

;*** ����������� ���������� ���� ������� ***

.def temp = r16 ;������ ����������� ���������
.def long_delay_low = r24 ;�������� ���� ��������� ����� ��������
.def long_delay_high = r25 ;������� ���� ��������� ����� ��������
.def counter = r17; �������� ����� � ��������� ��������� ��������

;*** ����������� �������� ***

;������ ������� �������� ���'��, �� ������� �� ������ ����� ���������
.EQU led_line = 0xA006

;��������� ����� ��� ����������� ���������
;����� ������� � �������� �� ������� ������� ����������� �������� ���������
.EQU t1 = 0b00000000
.EQU t2 = 0b00011000
.EQU t3 = 0b00100100

;***** ������� �������� *****

.CSEG		;��������� ������� �������� ����		
.ORG 0x0000	;��������� ������ ������� �������� ���� � ���'�� �������	

;*** ����������� ������� ������� ���������� �������������� ***

	rjmp Init; ������ ����������� �� ��������
	reti; rjmp EXT_INT0; IRQ0 Handler
	reti; rjmp EXT_INT1; IRQ1 Handler
	reti; rjmp TIM1_CAPT; Timer1 Capture Handler
	reti; rjmp TIM1_COMPA; Timer1 Compare A Handler
	reti; rjmp TIM1_COMPB; Timer1 Compare B Handler
	reti; rjmp TIM1_OVF; Timer1 Overflow Handler
	reti; rjmp TIM0_OVF; Timer0 Overflow Handler
	reti; rjmp SPI_STC; SPI Transfer Complete Handler
	reti; rjmp USART_RXC; USART RX Complete Handler
	reti; rjmp USART_UDRE; UDR0 Empty Handler
	reti; rjmp USART_TXC; USART TX Complete Handler
	reti; rjmp ANA_COMP; Analog Comparator Handler
	reti; rjmp EXT_INT2; IRQ2 Handler
	reti; rjmp TIM0_COMP; Timer0 Compare Handler
	reti; rjmp EE_RDY; EEPROM Ready Handler
	reti; rjmp SPM_RDY; Store Program memory Ready

;*** ��������� ����������� �������������� ***

Init:

	ldi temp, low (RAMEND)	;����������� ��������� ����� SP
	out SPL, temp
	ldi temp, high (RAMEND)
	out SPH, temp		;��������� SP �� ������� ������ SRAM
	sbi ACSR, 7			;���������� �������� ����������� �����������

;���������� ������ �� ��������� ���'���� (��������� �� ���������� ����������)
	ldi temp, 0b10000000
	out MCUCR, temp

;*** ������ �� ���������������� ����������� �����������
;������������ �������� Z �� ������ ����� ���������
	ldi ZL, low (led_line)
	ldi ZH, high (led_line)

Infinite_loop:		;����������� ����

	ldi temp, t1	;������ ����� ����������� ��������� (2 ���� ������)
	st Z, temp		;������ ����� �� ���������

	rcall long_delay1; ��������� �������� ��������� �� 2 �

	ldi temp, t2	;������ ����� ����������� ��������� 
	st Z, temp		;������ ����� �� ���������

	rcall long_delay1; ��������� �������� ��������� �� 2 �

	ldi temp, t3	;������ ����� ����������� ��������� 
	st Z, temp		;������ ����� �� ���������

	rcall long_delay; ��������� �������� ��������� �� 1 �

	ldi temp, t2	;������ ����� ����������� ��������� 
	st Z, temp		;������ ����� �� ���������
	rcall long_delay1; ��������� �������� ��������� �� 2 �

	ldi temp, t1	;������ ����� ����������� ��������� 
	st Z, temp		;������ ����� �� ���������

	rcall long_delay; ��������� �������� ��������� �� 1 �


rjmp Infinite_loop	;������� �� ���� ������������ �����

;*** ϳ��������� ����� �������� ***

long_delay:
;* ���� � ��������� ���� ����������� ����� 9216 (2400h), �� �������� ���� ������� 1 �������

;* ��������� ������� ���������� ����������� ��� ������ � 7.3728 ��� ����:
;* 800 * ���������� �������� / (7.3728 * 1 000 000) = ���������� ��� � [�]

	ldi long_delay_low, 0x00;������������ � ���� ������� ����������� �������� 
	ldi long_delay_high, 0x24 ;(2400h)

long_delay1:
;* ���� � ��������� ���� ����������� ����� 9216 (2400h), �� �������� ���� ������� 1 �������

;* ��������� ������� ���������� ����������� ��� ������ � 7.3728 ��� ����:
;* 800 * ���������� �������� / (7.3728 * 1 000 000) = ���������� ��� � [�]

	ldi long_delay_low, 0x00;������������ � ���� ������� ����������� �������� 
	ldi long_delay_high, 0x48 ;(4800h)

long_loop:	;��� ����� ����� 796 + 2 + 2 = 800 �����
	rcall short_delay		;������� ��������		
	sbiw long_delay_high: long_delay_low, 0b00000001	;�������� � ���� ����� 1 (��������� ������� ���������)
	brne long_loop	;���� �� 0, ��������� ����
	ret			;���������� � ������� ��������

;*** ϳ��������� ������� �������� (������� ��� ��������� ������ ��������) ***
short_delay:; ��� ���������� ����� ���� 796 ����� ����� �� rcall �� ret

	nop
	ldi counter, 0xC5	;�������� �����
short_loop:
	nop
	dec counter
	brne short_loop	;������� ������������ �� �������� ���� (����������)

	ret			;���������� �� ������� ��������

.EXIT				;����� ��������
