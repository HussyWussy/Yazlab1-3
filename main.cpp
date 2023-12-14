#include <iostream>
#include <semaphore>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <atomic>
#include <vector>
#include <Windows.h>


std::counting_semaphore<6> masalar(6);
std::condition_variable cv;
std::mutex cv_m;
int nextCustomer = 0;

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
		std::cout << number << " çalýþmaya baþladý " << std::endl;


		std::unique_lock<std::mutex> lk(cv_m);
		cv.wait(lk, [&] { return nextCustomer == Priority; });
		lk.unlock();
		

		
		masalar.acquire();
		std::cout << number << " masaya oturdu " << std::endl;
		std::unique_lock<std::mutex> lk2(cv_m);
		nextCustomer++;
		cv.notify_all();
		lk2.unlock();




		std::this_thread::sleep_for(std::chrono::seconds(1));

	
		std::cout << number << " masadan kalkt " << std::endl;
		masalar.release();
	
	}
	std::thread cthread;



};


int main() {


	Customer test;

	Customer test1;
	test1.Priority = 1;

	Customer test2;
	test2.Priority = 2;


	test2.TrySitting(2);
	test1.TrySitting(1);
	test.TrySitting(0);



	test.joinTh();
	test1.joinTh();
	test2.joinTh();



	return 0;








}