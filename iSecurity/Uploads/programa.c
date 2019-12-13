#include <iostream>
using namespace std;

int sumaMult5(int );

int main() {
	int n;
	
	cout << "Introduce un número límite para la suma de los múltiplos de 5: ";
	cin >> n;
	
	cout << sumaMult5(n) << endl;
	
}

int sumaMult5(int n) {
	int suma,x;
	
	if(n<5)
		suma=0;
	else{
		x++;
		suma=sumaMult5(n-5)+5*x;
	}
	return (suma);
}

// Mirar en 'Prácicas de otros años'