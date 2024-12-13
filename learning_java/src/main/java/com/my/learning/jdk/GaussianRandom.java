package com.my.learning.jdk;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GaussianRandom {
	public static void main(String[] args) throws IOException {
		Random r = new Random();
		// Map<Double, Integer> map = new TreeMap<Double, Integer>();
		// for (int i = 0; i < 10000; i++) {
		//
		// double gau = r.nextGaussian();
		// double gauKey = Math.round(gau*10);
		// System.out.println(gauKey);
		// }
		//
		// for (Entry entry : map.entrySet()) {
		// System.out.printf("%f %d%n", entry.getKey() , entry.getValue());
		// }

		//
		// List<Integer> list = r.ints(30, 0,
		// 6).boxed().collect(Collectors.toList());
		// System.out.println(list);
		// list.stream().distinct().map((i)->i.toString()).reduce((i,j)->String.join(",
		// ", i,j)).ifPresent(System.out::println);
		//
		// System.out.println(list);
		// Map<Integer, List<Integer>> map =
		// list.stream().collect(tors.groupingBy((i)->i));
		// System.out.println(map);
		// Map<Integer, Long> map2
		// =list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.groupingBy((i)->i,LinkedHashMap::new,Collectors.counting()));
		// map2.forEach((k,v)->System.out.print(k+": "+v+", "));
		// map2.compute(0, (k,v)->{
		// return v;
		// });
		;
//		List<Double> list = new ArrayList<>();
//		for (int i = 0; i < 1000; i++) {
//			double key = Math.round(r.nextGaussian() * 10);
//			list.add(key);
//		}
//
//		Map<Double, Long> map = list.stream().collect(Collectors.groupingBy((i) -> i, TreeMap::new, Collectors.counting()));
//		;
//		System.out.println(map);
		
		List<Integer>list = r.ints(10, 0, 10).boxed().collect(Collectors.toList());
		System.out.println(list);
		System.out.println(Arrays.toString(list.stream().toArray(Integer[]::new)));
		
		List<Integer> numbers = Arrays.asList(0,1, 2, 3, 4, 5, 6,7,8,9); 
		
		int skip = 0;
		int subSize = 2;
		while(skip<numbers.size()){
			numbers.stream().skip(skip).limit(subSize).map((i)->i.toString()).reduce((i,j)->String.join(", ", i,j)).ifPresent(System.out::println);
			skip+=subSize;
		}
		
		numbers.stream().peek((i)->{
			System.out.print(i);
		}).count();
	}

	double[] generateDoubleArray(int length){
		final Random random = new Random();
		Generator<Double> generator = ()->(double)Math.round(random.nextGaussian() * 10);
		double[] ds = new double[length];
		for (int i = 0; i < length; i++) {
			ds[i] = generator.generate();
		}
		return ds;
	}
	
	static interface Generator<T>{
		T generate();
	}
	
//	static class DoubleGenerator implements Generator<Double>{
//		
//		@Override
//		public Double generate() {
//			return Math.round(random.nextGaussian() * 10);
//		}
//	}
	
	double doubleGenerator(Random random) {
		return Math.round(random.nextGaussian() * 10);
	}
}
