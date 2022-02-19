/**�������� �� ����������� ������ �3 (����� EV8031/AVR) ***
***�������� � ��������� �������������� ����������� ***
***³��������� �� ���������� ��������������� ��������� ����� 1234 ***/

#define F_CPU 7372800L //������ ������� ������ (7,3728 ���)
#include <avr/io.h>
#include <avr/iom8515.h>
#include <util/delay.h>

//������ ������� ����� ��� ��������� ���������� �������������� ����������
#define dyn_7seg_data 0x8001
//������ ������� ������ ����� ����������������� ���������� � ���������
//������������ ��������� ���������� ��������������� ����������
#define synthes_5x7_row_dyn_7seg_control 0x8002
//��������� ��������� �������� (�� �������� ������� ��������),
//����� ��� ��������� ����� �� ������ ��������� ����������
#define waiting 5

int main (void) {

//��������� ������������ ����������
//���������� ������ �� ���������� ���'���� (��������� �� ���������� ���������� ������)
	MCUCR = 1 << SRE;
//���������� �������� ����������� �����������
	ACSR = 1 << ACD;
volatile unsigned char* A = (unsigned char *) 0x0090;
volatile unsigned char* B = (unsigned char *) 0x0100;
volatile unsigned char* D = (unsigned char *) 0x0110;
volatile unsigned char* C = (unsigned char *) 0x0120;
 *A = 0xBF;//(10111111) 0
 *(A+1) = 0x86;//(10000110)1
 *(A+2) = 0xDB;//(11011011)2
 *(A+3) = 0xCF;//(11001111)3
 *(A+4) = 0xE6;//(11100110)4
 *(A+5) = 0xED;//(11101101)5
 *(A+6) = 0xFD;//(11111101)6
 *(A+7) = 0x87;//(10000111)7
 *(A+8) = 0xFF;//(11111111)8
 *(A+9) = 0xEF;//(11101111)9

 *B = 0xBF;//(10111111) 0
 *(B+1) = 0x86;//(10000110)1
 *(B+2) = 0xDB;//(11011011)2
 *(B+3) = 0xCF;//(11001111)3
 *(B+4) = 0xE6;//(11100110)4
 *(B+5) = 0xED;//(11101101)5
 *(B+6) = 0xFD;//(11111101)6
 *(B+7) = 0x87;//(10000111)7
 *(B+8) = 0xFF;//(11111111)8
 *(B+9) = 0xEF;//(11101111)9

 *C = 0xBF;//(10111111) 0
 *(C+1) = 0x86;//(10000110)1
 *(C+2) = 0xDB;//(11011011)2
 *(C+3) = 0xCF;//(11001111)3
 *(C+4) = 0xE6;//(11100110)4
 *(C+5) = 0xED;//(11101101)5
 *(C+6) = 0xFD;//(11111101)6
 *(C+7) = 0x87;//(10000111)7
 *(C+8) = 0xFF;//(11111111)8
 *(C+9) = 0xEF;//(11101111)9

 *D = 0xBF;//(10111111) 0
 *(D+1) = 0x86;//(10000110)1
 *(D+2) = 0xDB;//(11011011)2
 *(D+3) = 0xCF;//(11001111)3
 *(D+4) = 0xE6;//(11100110)4
 *(D+5) = 0xED;//(11101101)5
 *(D+6) = 0xFD;//(11111101)6
 *(D+7) = 0x87;//(10000111)7
 *(D+8) = 0xFF;//(11111111)8
 *(D+9) = 0xEF;//(11101111)9

unsigned int count1;	
unsigned int count2 = 1;
//������������ �������� �� ������ ������� ����� ���������� ����������
	volatile unsigned char* pd = (unsigned char *) dyn_7seg_data;
// ������������ �������� �� ������ ������� ��������� ��������� �����������
	volatile unsigned char* pc = (unsigned char*) synthes_5x7_row_dyn_7seg_control;

	while (1) { //����������� ����
	
		if (count2 = 1){
		for (count1 = 50; count1 <=50; count1++){ // 1 c������
		*pd = *(D+5);
		*pc = 0x00; //��������� ������ ��� ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��


		*pd = *(C+9);
		*pc = 0x01; //��������� ����� ���� ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��


		*pd = *(B+5);
		*pc = 0x02; //��������� ����� ������ ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��


		*pd = *(A+9);
		*pc = 0x03; //��������� ������ ����� ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��
	}
			if(*D){*D=*(D+5); count2 --;}
			if(*C){*C=*(C+9); *(D+5)=*(D-1);}

			if(*B){*B=*(B+5); *(C+9)=*(C-1); }

			if(*A){*(B+5)=*(B-1); *A=*(A+9); }
			*(A+9)=*(A-1);
		
			}
	
		if (count2 = 0){
		for (count1 = 50; count1 <=50; count1++){ // 1 c������
		*pd = *D; 
		*pc = 0x00; //��������� ������ ��� ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��


		*pd = *C; 
		*pc = 0x01; //��������� ����� ���� ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��


		*pd = *B; 
		*pc = 0x02; //��������� ����� ������ ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��


		*pd = *A; 
		*pc = 0x03; //��������� ������ ����� ���������
		_delay_ms (waiting); //��������� �������� �� 5 ��
	}
			if(*(D+5)){*(D+5)=*D; count2 ++;}
			if(*(C+9)){*(C+9)=*C; *D=*(D+1);}

			if(*(B+5)){*(B+5)=*B; *C=*(C+1); }

			if(*(A+9)){*(B)=*(B+1); *(A+9)=*A; }
			*(A)=*(A+1);
		
}}
	return 0;
}
