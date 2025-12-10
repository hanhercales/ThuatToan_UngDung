#include<stdio.h>
#include<math.h>
main()
{
	int n;
	float x,tu,mau,bieuthuc,s;
	back: printf("nhap nguyen duong n: ");
	scanf("%d",&n);
	if(n<=0)goto back;
	printf("nhap so thuc x: ");
	scanf("%f",&x);
	float a[n];
	loican: loi0: printf("nhap cac gia tri cua day a: ");
	for(int i=0;i<n;i++)
	{
		{
			scanf("%f",&a[i]);
		}
		{
			tu+=pow(x,i+1);
		}
		{
			mau+=a[i];
		}
	}
	if(mau==0) goto loi0;
	s=sqrt(10+tu/mau);
	if(s<0) goto loican;
	printf("gia tri cua s la: %f",s);
}
{
	int n;
	float x,tu,mau,bieuthuc,s;
	back: printf("nhap nguyen duong n: ");
	scanf("%d",&n);
	if(n<=0)goto back;
	printf("nhap so thuc x: ");
	scanf("%f",&x);
	float a[n];
	loican: loi0: printf("nhap cac gia tri cua day a: ");
	for(int i=0;i<n;i++)
	{
		{
			scanf("%f",&a[i]);
		}
		{
			tu+=pow(x,i+1);
		}
		{
			mau+=a[i];
		}
	}
	if(mau==0) goto loi0;
	s=sqrt(10+tu/mau);
	if(s<0) goto loican;
	printf("gia tri cua s la: %f",s);
}