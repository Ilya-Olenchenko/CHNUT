;*** �������� �� ������������-�������� ������ (����� EV8031) *****

.include "m8515def.inc"	;���������� ������ ���������� ATmega8515

;*** ����������� ���������� ���� ������� ***

.def diode = r16		;������, ���� ������ ���� ����������� �����
.def temp = r17		;������ ����������� ���������
.def encoder_data = r18	;�������� �������� ��������

;������, �� ������ ��������� ���� ���������������� ��������
.def last_encoder_state = r19
;������, �� ������ ��������� ���� ���������������� ��������
.def next_encoder_state = r20

.def attribute = r23	; ������� 

.def count_encoder_forward = r24
.def count_encoder_back = r25
.def gray_data = r15

;*** ����������� �������� ***

.EQU led_line = 0xA006; ������ ����� ��������� � �����

;***** ������� �������� *****

.CSEG		;��������� ������� �������� ����
.ORG 0x0000	;��������� ������ ������� �������� ���� � ���'�� �������

;*** ������� ������� ���������� ���������� ***

	rjmp Init; Reset Handler (������ ����������� �� ��������)
	reti; rjmp EXT_INT0; IRQ0 Handler
	reti; rjmp EXT_INT1; IRQ1 Handler
	reti; rjmp TIM1_CAPT; Timer1 Capture Handler
	rjmp TIM1_COMPA; Timer1 Compare A Handler
;�����, ������ �������� �� �������� ����������� �� ��������� A �������/��������� T1
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

;*** ��������� ����������� ���������� ***

Init:
	ldi temp, low (RAMEND)
	out SPL, temp
	ldi temp, high (RAMEND)
	out SPH, temp	;��������� SP �� ������� ������ � SRAM
	sbi ACSR, 7		;���������� �������� ����������� �����������

	ldi temp, 0b10000000	;���������� ������ �� ��������� ���'����
	out MCUCR, temp

	;������������ �������� Z �� ������ ����� ��������� � �����
	ldi ZL, low (led_line)
	ldi ZH, high (led_line)

	clr diode	;������� ������ ����� ���������� ����� (������ �� ����)
	clr encoder_data	;������� ������ ���� ��������, �� ������� � ��������

	in last_encoder_state, PINB	;������ �������� ���� ������ ��������

;�������� ���� ��� �����, �� ���������� �� �������� ���� ��������
	andi last_encoder_state, 0b10010000

;*** ������������ ���������� ***

	ldi temp, (1 << OCIE1A)	;�������� ����� � ������ temp
	out TIMSK, temp		;�������� � ������ TIMSK (���������� �����������
					;�� ����� A �������/��������� T1)

;*** ������������ �� ���� �������/��������� T1 ***

;����������� � ���� ������� OCR1AH: OCR1AL ����� 0x0006, ����� ������
;��������, ��� ��������� ����� ��������/���������� T1 ����
;����������� ����������� �� ��������� A
	ldi temp, 0x00
	out OCR1AH, temp
	ldi temp, 0x06
	out OCR1AL, temp
	ldi temp, (1 << PSR10)	;������� �����������, ��������� 1 � �� PSR10,
	out SFIOR, temp		;�������� � ��� ������ ���������� ������� �����						;(�������� ������������)

;����������� � ���� ������� TCCR1A: TCCR1B ����� 0x000C
	ldi temp, 0b00000000
	out TCCR1A, temp	;������ ����� ������ CTC � ������ ���������
	ldi temp, (1 << WGM12) | (1 << CS12) | (1 << CS10)	;������/�������� T1, ����������� ����������� �� 1024
	out TCCR1B, temp		;!� ����� ���� ���� ���������� ���� �������, ���� ����������� ��� ������!

	ldi attribute, 0x00;
	ldi count_encoder_forward, 0x00;
	ldi count_encoder_back, 0x00;
;*** ���������� ������ ������������ ���������� ***

	sei	;������������ ��������� ����������� ������� ����������
		;����������� ����� ����������� TIM1_COMPA),
		;����� ����������� �� ��������� A �������/��������� �1


;�������, �� � ��������� ���� ������� ���� �� �����������, �����
	;� ������� � ���� ������ (����� B - PB4; ����� A - PB7) ���������� "0"
	;*** ���������� � ����������� ���� ���������� ����� �������� ***

Infinite_loop:; ����������� ����

	cpi attribute, 0xFF;
	breq Check_Encoder;
	nop
	st Z, diode; �������� �������� ��������� �� ���������� �����
	nop
	rjmp Infinite_loop; ����� �� ������� ������������ �����

Check_Encoder:
	in next_encoder_state, PINB	;������ �������� ���� ������ ��������

;�������� ���� ��� �����, �� ���������� �� �������� ���� ��������
	andi next_encoder_state, 0b10010000

	cp next_encoder_state, last_encoder_state
	breq Next_iterate	;���� ���� �������� �� ������� - ���������� ���� ������

	cpi last_encoder_state, 0b00000000	;���� � ����� � �������� �� ���� "0" � � ����� � ��� �� ���� "0"
	brne Next_iterate; ���������� �� ��������� ��

	cpi next_encoder_state, 0b00010000	;���� � ����� A �������� ��������� "0", � � ����� B �'������� "1", �� ������� ��������� ����� ������������ �������
	breq Decr_state		;���������� �� ��������� ����� ��������

	cpi next_encoder_state, 0b10000000	;���� � ����� A �������� �'������� "1", � � ����� B ��������� "0", �� ������� ��������� �� ������������ �������
	breq Incr_state		;���������� �� ��������� ����� ��������

	rjmp Next_iterate	;���� � ������� � ������ �� ������ ����� - ����� ���

Incr_state:
	inc count_encoder_forward
	clr count_encoder_back;	
	ldi temp, 0x02
	cp temp, count_encoder_forward
	breq Increment
	rjmp Next_iterate

Increment:
	clr count_encoder_forward
	inc encoder_data	;��������� ������� ��������, ��������� �� ��������
	mov temp, encoder_data
	mov gray_data, encoder_data
	lsr temp
	eor gray_data, temp
	rjmp Next_iterate	;���������� �� ��������� ��

Decr_state:
	inc count_encoder_back;
	clr count_encoder_forward
	ldi temp, 0x02
	cp temp, count_encoder_back
	breq Decrement
	rjmp Next_iterate

Decrement:
	clr count_encoder_back
	dec encoder_data	;��������� ������� ��������, ��������� �� ��������
	mov temp, encoder_data
	mov gray_data, encoder_data
	lsr temp
	eor gray_data, temp
	rjmp Next_iterate

Next_iterate:	;������� �� �� ���� ���������� � ����-����� �������!
	in temp, PINB	;������ �������� ���� ������ ��������

	;�������� ���� ��� �����, �� ������� �� ���������� �������� �� ������
	andi temp, 0b00100000

	tst temp	;���� ������� ��������� �� ������, �� ��������� �������� ��������, ��������� �� �����
	brne Led_action		;� ���� � - ����� ���
	//ldi temp, 0x88
	//mov gray_data, temp

Led_action:
	mov diode, gray_data	;��������� ���� ������� ����� ����� ���������
	mov last_encoder_state, next_encoder_state	;��������� �� �������� �������� ���������� ����� ��������
	;���������� ������������ ����� �������� ���� �������� ����
	ldi attribute, 0x00;
	rjmp Infinite_loop; ����� �� ������� ������������ �����

TIM1_COMPA:		;�������� ���������� ����������� TIM1_COMPA (��� ���� I = 0)
	
	ldi attribute, 0xFF;
	reti	;���������� �� �����������
		;��� ����� ����������� �������������� ��������� I
		;� ����� ���������� ������� ����������


.EXIT; ����� ��������
