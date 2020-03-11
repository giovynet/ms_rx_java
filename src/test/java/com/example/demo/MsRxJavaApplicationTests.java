package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

@SpringBootTest
class MsRxJavaApplicationTests {

	@Test
	void contextLoads() {

		/**
		 * Observable<String> myStrings = Observable.just("Alpha", "Beta", "Gamma",
		 * "etc"); myStrings.subscribe(s -> System.out.println(s));
		 * 
		 * Observable<Long> observableInervals = Observable.interval(1,
		 * TimeUnit.SECONDS); observableInervals.subscribe(s -> System.out.println(s));
		 * 
		 * //Mantiene el hilo por 10 seg antes de terminar //para que el observable
		 * tenga chance de disparar //sleep(10000);
		 * 
		 * Observable<String> observableSource = Observable.create(emitter -> { try {
		 * emitter.onNext("Alpha"); emitter.onNext("Beta"); emitter.onNext("Gamma");
		 * emitter.onComplete();
		 * 
		 * }catch (Exception e) { emitter.onError(e);; } }); Observable<Integer> lengths
		 * = observableSource.map(String::length); Observable<Integer> filtered =
		 * lengths.filter(i -> i > 4); filtered.subscribe(s ->
		 * System.out.println("RECEIVED: " + s));
		 * 
		 * List<String> items = List.of("Alpha", "Beta", "Gamma"); Observable<String>
		 * obsIterable = Observable.fromIterable(items);
		 * //obsIterable.map(String::length).filter(i -> i<5).subscribe( s ->
		 * System.out.println("RECEIVED: " + s ));
		 **/

	}

	@Test
	@Disabled("Un coment for run")
	void subscripcionDeObservadarAObservable() {
		System.out.println(" ==  EJEMPLO SIN LAMBDAs  ==");

		// Crea la fuente observable
		List<String> itemsDos = List.of("Alpha", "Beta", "Gamma");
		Observable<String> fuenteO1 = Observable.fromIterable(itemsDos);

		// Crea un observador
		Observer<Integer> myObserver = new Observer<>() {
			@Override
			public void onSubscribe(Disposable d) {
				// do nothing with Disposable, disregard for now
				System.out.println("Inicia cadena...");
			}

			@Override
			public void onNext(Integer value) {
				System.out.println("RECEIVED: " + value);
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Finaliza cadena!");
			}
		};

		// Subscribe el observador la fuente.
		fuenteO1.map(String::length).filter(i -> i < 5).subscribe(myObserver);

		System.out.println(" == EJEMPLO CON LAMBDAS == ");
		// Crea un observador

		Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");
		source.map(String::length).filter(i -> i < 5).subscribe(i -> System.out.println("RECEIVED: " + i),
				Throwable::printStackTrace, () -> System.out.println("Done!"));
	}

	@Test
	//@Disabled("Un coment for run")
	void observableFrioEnDosObservadores() {
		/**
		 * Es muy similar a un CD de música que 
		 * se proporciona a cada oyente, 
		 * por lo que cada persona puede escuchar 
		 * todas las pistas cada vez que comienzan 
		 * a escucharlo
		 */

		Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");
		//first observer
		source.subscribe(s -> System.out.println("Observer One: " + s));
		//second observer
		source.subscribe(s -> System.out.println("Observer Two: " + s));
		
		/**
		 * Incluso si el segundo observador transforma sus emisiones con 
		 * los operadores, seguirá recibiendo su propio flujo de emisiones.
		 */
        source.map(String::length)
              .filter(i -> i >= 5)
              .subscribe(s -> System.out.println("Observer 1: " + s));

        source.subscribe(s -> System.out.println("Observer 2: " + s));
        
        
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
