package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.indicator.QualityEvaluation;

public class ParetoUtils {
	
	public static void swapColumns(double[][] front, int colA, int colB) throws Exception{
		
		if(front.length == 0 || (colA > front.length) || (colB > front.length))
			throw new Exception("Selected dimensions exceed front size");
		else{
					
			int setPointMin = Math.min(colA, colB);
			int setPointMax = Math.max(colA, colB);
			
			for(int i=0;i<front.length;i++){
				double back = 0.0;
				for(int j=0;j<front[0].length; j++){
					if(j==setPointMin){
						back = front[i][j];
						front[i][j] = front[i][setPointMax];
					}
					if(j==setPointMax){
						front[i][j] = back;
					}
				}
			}
		}		
		
	}
	
	public static void joinParetoFiles(String path, String baseName, int numRuns, String out) throws IOException{
		
		FileWriter fw = new FileWriter(path+out);
		BufferedWriter bw = new BufferedWriter(fw);
		
		System.out.print("Reading");
		
		for(int i = 1; i <= numRuns; i++){
			String filePath = path+baseName;
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready()){
				System.out.print(".");
				String line = br.readLine();
				bw.append(line);
			}			
		}
		System.out.println("\nDone! -> generated ["+path+out+"]");
	}
	
	public static double[][] joinParetos(double[][] pareto1, double[][] pareto2) throws Exception{
		if(pareto1==null){
			if(pareto2!=null)
				return pareto2;
		}
		else if(pareto2==null){
			if(pareto1!=null)
				return pareto1;
		}
		
		if(pareto1[0].length!=pareto2[0].length){
			throw new Exception("Cannot merge pareto fronts with different number of objetives");
		}
		else{
			
			int numObjs = pareto2[0].length;
			int par1size = pareto1.length;
			int par2size = pareto2.length;
			ArrayList<double[]> newPareto = new ArrayList<double[]>(par1size+par2size);
			for(int i=0;i<pareto1.length;i++){
				if(!solutionIsInFront(pareto1[i],newPareto))
					newPareto.add(pareto1[i]);
			}
			for(int i=0;i<pareto2.length;i++){
				if(!solutionIsInFront(pareto2[i],newPareto))
					newPareto.add(pareto2[i]);
			}

			double[][] newParetoArray = new double[newPareto.size()][numObjs];
			newParetoArray = newPareto.toArray(newParetoArray);			
			
			return newParetoArray;
		}
	}
	
	/**
	 * 
	 * @param toFilter the Pareto front to be filtered by repetitions
	 * @return the filtered pareto front.
	 */
	public static List<double[]> filterRepetitions(List<double[]> toFilter){
		ArrayList<double[]> filtered = new ArrayList<double[]>();
		
		for(double[] sol : toFilter)
			if(!solutionIsInFront(sol, filtered))
				filtered.add(sol);
		
		return filtered;
	}
	
	
	public static double[][] filterRepetitions(double[][] toFilter){
		ArrayList<double[]> filtered = new ArrayList<double[]>();
		int numObjs = toFilter[0].length;
		
		for(double[] sol : toFilter)
			if(!solutionIsInFront(sol, filtered))
				filtered.add(sol);
		
		double[][] filteredArr = new double[filtered.size()][numObjs];
		filteredArr = filtered.toArray(filteredArr); 
		
		return filteredArr;
	}
	
	public static boolean solutionIsInFront(double[] sol, List<double[]> front){
		boolean isIn = false;
		
		for(double[] compSol : front){
			if(solutionIsEqual(sol, compSol)){
				isIn = true;
				break;
			}
		}
		
		return isIn;
	}

	
	public static boolean solutionIsEqual(double[] sol1, double[] sol2){
		
		boolean allEqual = true;
		
		for(int i=0;i<sol1.length; i++){
			if(sol1[i]!=sol2[i]){
				allEqual = false;
				break;
			}
		}
		
		return allEqual;
	}
	
	public static void filterInfinity(String in,String out) throws IOException{
		FileWriter fw = new FileWriter(out);
		BufferedWriter bw = new BufferedWriter(fw);

		System.out.print("Reading");

		FileReader fr = new FileReader(in);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			System.out.print(".");
			String line = br.readLine();
			if(!line.contains("Infinity")){
				bw.append(line);
				bw.newLine();
			}
		}

		br.close();
		fr.close();
		bw.close();
		fw.close();
		System.out.println("\nDone! -> generated ["+in+out+"]");
	}
	
	public static double[][] readFront(String path,String sep,int numObjs) throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<ArrayList<Double>> front = new ArrayList<ArrayList<Double>>();
		
		while(br.ready()){
			ArrayList<Double> lineArray = new ArrayList<Double>();
			
			String line = br.readLine();
			String[] tokens = line.split(sep);
			for(String tk:tokens)
				lineArray.add(Double.parseDouble(tk));
			
			front.add(lineArray);
		}
		
		br.close();
		fr.close();
		
		double[][] toret = new double[0][0];
		if(front!=null && front.size()>0)
			toret = new double[front.size()][front.get(0).size()];
		
		for(int i = 0;i< front.size();i++)
			for(int j = 0; j< front.get(0).size();j++)
				toret[i][j] = front.get(i).get(j);
		
		return toret;
	}
	
	public static ArrayList<ArrayList<String>> readFrontSolution(String path,String sep) throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<ArrayList<String>> front = new ArrayList<ArrayList<String>>();
		
		while(br.ready()){
			ArrayList<String> lineArray = new ArrayList<String>();
			
			String line = br.readLine();
			String[] tokens = line.split(sep);
			for(String tk:tokens)
				lineArray.add(tk);
			
			front.add(lineArray);
		}
		
		br.close();
		fr.close();
		
		return front;
	}
	
	
	public static void filterRepetitions(String filepath, String file2save){
		QualityEvaluation eval = new QualityEvaluation();
		
		double[][] front = eval.readFrontFromFile(filepath,",");
		ArrayList<double[]> newfront = new ArrayList<double[]>();
		
		for(double[] sol:front){
			boolean diff = true;
			for(double[] sol2:newfront){				
				for(int i=0;i<sol2.length;i++){
					if(sol[i]==sol2[i])
						diff=false;
				}
			}
			if(diff)
				newfront.add(sol);
		}
		
		
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(file2save));
			for(int i = 0;i<newfront.size();i++){
			double[] line = newfront.get(i);
				int k=line.length;
				for(double d: line){
					bf.append(String.valueOf(d));
					if(k>1)
						bf.append(",");
					k--;
				}
				bf.newLine();
			}
			bf.flush();
			bf.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done! Front was ["+front.length+"] and now is ["+newfront.size()+"].");
	}
	
	public static void filterNonDominated(String filepath, String file2save,int numObjectives){

		QualityEvaluation eval = new QualityEvaluation();

		double[][] front = eval.readFrontFromFile(filepath,",");

		int n = MOUtils.filterNonDominatedFront(front,front.length,numObjectives);

		System.out.println("N: "+n);
		System.out.println("Front size:"+front.length);

		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(file2save));
			for(int i = 0;i<n;i++){
				double[] line = front[i];
				int k=line.length;
				for(double d: line){
					bf.append(String.valueOf(d));
					if(k>1)
						bf.append(",");
					k--;
				}
				bf.newLine();
			}
			bf.flush();
			bf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String... args){
		String basePath = "/home/pmaia/uminho/mestrado/2ano/tese/results/2OF/merge_lac/";
//		String basePath = "/home/pmaia/uminho/mestrado/2ano/tese/results/3OF/ecoli_SPEA2_succ_minfluxsum/projection/";
//		String basePath = "/home/pmaia/uminho/mestrado/2ano/tese/results/3OF/ecoli_NSGAII_succ_minknocks/projection/";
//		String basePath = "/home/pmaia/uminho/mestrado/2ano/tese/results/3OF/ecoli_NSGAII_succ_minfluxsum/projection/";
//		String basePath = "/home/pmaia/uminho/mestrado/2ano/tese/results/3OF/ecoli_NSGAII_succ_minknocks/";
		
		String fileIn = "reference_full.csv";
		String fileOutNoInfs = "reference_full.noInfsPareto";
//		String fileOutNoReps = "referenceNSGA.noRepsPareto";
		String fileOutNoDoms = "reference_full.noDomsPareto";
		
		try {
			filterInfinity(basePath+fileIn, basePath+fileOutNoInfs);
			filterNonDominated(basePath+fileOutNoInfs,basePath+fileOutNoDoms,2);
//			filterRepetitions(basePath+fileOutNoDoms,basePath+fileOutNoReps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
