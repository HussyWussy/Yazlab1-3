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
	/*
	Customer c1(1);
	Customer c2(2);
	Customer c3(3);
	Customer c4(4);
	Customer c5(5);
	Customer c6(6);
	Customer c7(7);
	Customer c8(8);
	Customer c9(9);
	Customer c10(10);

	c1.TrySitting();
	c2.TrySitting();
	c3.TrySitting();
	c4.TrySitting();
	c5.TrySitting();
	c6.TrySitting();
	c7.TrySitting();
	c8.TrySitting();
	c9.TrySitting();
	c10.TrySitting();
	*/

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