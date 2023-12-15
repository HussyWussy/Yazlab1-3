#include <iostream>
#include <semaphore>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <atomic>
#include <vector>
#include <Windows.h>

std::condition_variable cv;
std::mutex cv_m;
std::mutex mutex;
int nextCustomer = 0;

std::counting_semaphore<6> tables(6);

class Customer {
public:
	int Priority = 0;
	Customer() = default;
	void TrySitting(int numberOfCustomer)
	{
		cthread = std::thread(&Customer::sitOnTable, this, numberOfCustomer);
	}
	void joinTh() {
		cthread.join();
	}
private:

	void sitOnTable(int number)
	{
		//std::cout << number << " calismaya basladi " << std::endl;

		//burada �al��maya ba�las�n m� diye bak�l�yor
		//beklemeyi �al��maya ba�las�n diye demi yoksa masa beklerken mi yapsak acaba?

			std::unique_lock<std::mutex> lk(cv_m);
			//buraya waitfor ile bi�eyler dene filan fi�man i�te yap mq
			cv.wait(lk, [&] { return nextCustomer == Priority; });
			//std::cout << number << "  bekliyor " << std::endl;
			
			lk.unlock();
			
			
			


			tables.acquire();
			
				std::unique_lock<std::mutex> lk2(cv_m);
				std::cout << number << " masaya oturdu " << std::endl;
				nextCustomer++;
				cv.notify_all();
				lk2.unlock();
				std::this_thread::sleep_for(std::chrono::seconds(2));
				tables.release();
				//std::cout << number << " masadan kalkt " << std::endl;


			
			
				//std::cout << number << " ben gidiyom s2m boyle isi " << std::endl;
				//nextCustomer++;
				//cv.notify_all();

			
			



	}
	std::thread cthread;



};

class PriorityCustomer : public Customer {
	
public:
	
	PriorityCustomer() = default;

	
};

int main() {
	



	Customer test;
	test.Priority = 0;

	Customer test1;
	test1.Priority = 1;

	Customer test2;
	test2.Priority = 2;

	Customer test3;
	Customer test4;
	Customer test5;
	Customer test6;
	Customer test7;
	Customer test8;

	test3.Priority = 3;
	test4.Priority =4;
	test5.Priority = 5;
	test6.Priority = 6;
	test7.Priority = 7;
	test8.Priority = 8;

	//zaten hangi m��terinin gelece�i en ba�ta se�ilece�i i�in �ncelik s�ras� en ba�ta birkere hesaplan�r runtime da hesaplanmas�na gerek yok herhalde diye d���n�yorum hoca �yle demi�ti ��nk�
	//bunlar� vekt�rlere atay�p otomatikle�tirebiliriz d�ng�ler ile ama �ncelik s�ras� �a�t�m� patl�yor 
	//�nceli�i elle girdi�imiz i�in zaten �ncelikli m��teri gibi bir class�n olmas� ne kadar mant�kl�
	//otomatik girersek de o girilen say�ya g�re olu�turaca��m�z i�in yine �nceli�i ona g�re vericez



	test2.TrySitting(2);
	test1.TrySitting(1);
	test.TrySitting(0);
	test3.TrySitting(3);
	test4.TrySitting(4);
	test5.TrySitting(5);
	test6.TrySitting(6);
	test7.TrySitting(7);
	test8.TrySitting(8);





	test.joinTh();
	test1.joinTh();
	test2.joinTh();
	test3.joinTh();
	test4.joinTh();
	test5.joinTh();
	test6.joinTh();
	test7.joinTh();
	test8.joinTh();



	return 0;








}