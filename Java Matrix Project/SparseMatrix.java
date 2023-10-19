/* 1922896
 * In the Add function, I have left in an if statement that results in the
 * wrong result being returned as it is used to remove any 0 values from
 * being stored in the matrix. Without the if statement, the correct result is
 * returned each time, however, if the value resulted in a 0 then it would
 * stored in the data structure as if it were non 0. I have left the if
 * statement in to demonstrate that an attempt was made but have put comments
 * next to each part that needs to be commented out for it to fully function.
 */

import java.util.*;
import java.io.*;
import java.util.Collections;

// A class that represents a dense vector and allows you to read/write its elements
class DenseVector {
	private int[] elements;

	public DenseVector(int n) {
		elements = new int[n];
	}

	public DenseVector(String filename) {
		File file = new File(filename);
		ArrayList<Integer> values = new ArrayList<Integer>();

		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextInt()) {
				values.add(sc.nextInt());
			}

			sc.close();

			elements = new int[values.size()];
			for (int i = 0; i < values.size(); ++i) {
				elements[i] = values.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read an element of the vector
	public int getElement(int idx) {
		return elements[idx];
	}

	// Modify an element of the vector
	public void setElement(int idx, int value) {
		elements[idx] = value;
	}

	// Return the number of elements
	public int size() {
		return (elements == null) ? 0 : (elements.length);
	}

	// Print all the elements
	public void print() {
		if (elements == null) {
			return;
		}

		for (int i = 0; i < elements.length; ++i) {
			System.out.println(elements[i]);
		}
	}
}

// A class that represents a sparse matrix
public class SparseMatrix {
	// Auxiliary function that prints out the command syntax
	public static void printCommandError() {
		System.err.println("ERROR: use one of the following commands");
		System.err.println(" - Read a matrix and print information: java SparseMatrix -i <MatrixFile>");
		System.err.println(" - Read a matrix and print elements: java SparseMatrix -r <MatrixFile>");
		System.err.println(" - Transpose a matrix: java SparseMatrix -t <MatrixFile>");
		System.err.println(" - Add two matrices: java SparseMatrix -a <MatrixFile1> <MatrixFile2>");
		System.err.println(" - Matrix-vector multiplication: java SparseMatrix -v <MatrixFile> <VectorFile>");
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			printCommandError();
			System.exit(-1);
		}

		if (args[0].equals("-i")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			System.out.println("The matrix has " + mat.getNumRows() + " rows and " + mat.getNumColumns() + " columns");
			System.out.println("It has " + mat.numNonZeros() + " non-zeros");
		} else if (args[0].equals("-r")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
		} else if (args[0].equals("-t")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			SparseMatrix transpose_mat = mat.transpose();
			System.out.println();
			System.out.println("Matrix elements:");
			mat.print();
			System.out.println();
			System.out.println("Transposed matrix elements:");
			transpose_mat.print();
		} else if (args[0].equals("-a")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat1 = new SparseMatrix();
			mat1.loadEntries(args[1]);
			System.out.println("Read matrix 1 from " + args[1]);
			System.out.println("Matrix elements:");
			mat1.print();

			System.out.println();
			SparseMatrix mat2 = new SparseMatrix();
			mat2.loadEntries(args[2]);
			System.out.println("Read matrix 2 from " + args[2]);
			System.out.println("Matrix elements:");
			mat2.print();
			SparseMatrix mat_sum1 = mat1.add(mat2);

			System.out.println();
			mat1.multiplyBy(2);
			SparseMatrix mat_sum2 = mat1.add(mat2);

			mat1.multiplyBy(5);
			SparseMatrix mat_sum3 = mat1.add(mat2);

			System.out.println("Matrix1 + Matrix2 =");
			mat_sum1.print();
			System.out.println();

			System.out.println("Matrix1 * 2 + Matrix2 =");
			mat_sum2.print();
			System.out.println();

			System.out.println("Matrix1 * 10 + Matrix2 =");
			mat_sum3.print();
		} else if (args[0].equals("-v")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			DenseVector vec = new DenseVector(args[2]);
			DenseVector mv = mat.multiply(vec);

			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
			System.out.println();

			System.out.println("Read vector from " + args[2] + ":");
			vec.print();
			System.out.println();

			System.out.println("Matrix-vector multiplication:");
			mv.print();
		}
	}

	// Loading matrix entries from a text file
	// You need to complete this implementation
	public void loadEntries(String filename) {
		File file = new File(filename);

		try {
			Scanner sc = new Scanner(file);
			numRows = sc.nextInt();
			numCols = sc.nextInt();
			entries = new ArrayList<Entry>();
			while (sc.hasNextInt()) {
				// Read the row index, column index, and value of an element
				int row = sc.nextInt();
				int col = sc.nextInt();
				int val = sc.nextInt();

				// Add your code here to add the element into data member entries
				int pos = (numCols * row) + col;
				entries.add(new Entry(pos, val));
			}
			// Add your code here for sorting non-zero elements
			mergeSort(entries, entries.size());
		} catch (Exception e) {
			e.printStackTrace();
			numRows = 0;
			numCols = 0;
			entries = null;
		}
	}

	// Default constructor
	public SparseMatrix() {
		numRows = 0;
		numCols = 0;
		entries = null;
	}

	// A class representing a pair of column index and elements
	private class Entry {
		private int position; // Position within row-major full array representation
		private int value; // Element value

		// Constructor using the column index and the element value
		public Entry(int pos, int val) {
			this.position = pos;
			this.value = val;
		}

		// Copy constructor
		public Entry(Entry entry) {
			this(entry.position, entry.value);
		}

		// Read column index
		int getPosition() {
			return position;
		}

		// Set column index
		void setPosition(int pos) {
			this.position = pos;
		}

		// Read element value
		int getValue() {
			return value;
		}

		// Set element value
		void setValue(int val) {
			this.value = val;
		}
	}

	// Adding two matrices
	public SparseMatrix add(SparseMatrix M) {
		SparseMatrix AMatrix = new SparseMatrix();
		ArrayList<Entry> added = new ArrayList<Entry>();
		ArrayList<Entry> secondMat = M.entries;
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (int i=0;i<this.entries.size();i++){
			Entry firstEntry = new Entry(this.entries.get(i).getPosition(), this.entries.get(i).getValue());
			added.add(firstEntry);
			positions.add(this.entries.get(i).getPosition());
		}
		for (int i=0;i<secondMat.size();i++){
			int pos = secondMat.get(i).getPosition();
			if (positions.contains(pos)){
				added.add(secondMat.get(i));
				int val = added.get(positions.indexOf(pos)).getValue()+secondMat.get(i).getValue();
				if (val!=0){ //comment out
					Entry newEntry = new Entry (pos,val);
					added.set(positions.indexOf(pos),newEntry);}
				} //comment out
				else{ //comment out
					added.add(secondMat.get(i));
				}}//comment out
		mergeSort(added,added.size());
		AMatrix.numRows = numRows;
		AMatrix.numCols = numCols;
		AMatrix.entries=added;
		return AMatrix;
	}

	// Transposing a matrix
	public SparseMatrix transpose() {
		ArrayList<Entry> transposed_entries = new ArrayList<Entry>();
		SparseMatrix TMatrix = new SparseMatrix();
		for (int i=0; i<entries.size();i++){
			int trans_pos = (numRows * (entries.get(i).getPosition()%numCols)) +
			(entries.get(i).getPosition()/numCols);
			transposed_entries.add(new Entry (trans_pos, entries.get(i).getValue()));
		}
		mergeSort(transposed_entries,transposed_entries.size());
		TMatrix.numRows = numCols;
		TMatrix.numCols = numRows;
		TMatrix.entries=transposed_entries;
		return TMatrix;
	}

	// Matrix-vector multiplication
	public DenseVector multiply(DenseVector v) {
		// Add your code here
		DenseVector multiplied = new DenseVector(numRows);
		for (int i=0;i<entries.size();i++){
			int newval = entries.get(i).getValue() * v.getElement(entries.get(i).getPosition() % v.size());
			int ind = entries.get(i).getPosition()/numCols;
			if (multiplied.getElement(ind)==0){
				multiplied.setElement(ind,newval);
			}
			else{
				multiplied.setElement(ind,(multiplied.getElement(ind) +newval));
			}
		}
		return multiplied;
	}

	// Return the number of non-zeros
	public int numNonZeros() {
		// Add your code here
		return this.entries.size();
	}

	// Multiply the matrix by a scalar, and update the matrix elements
	public void multiplyBy(int scalar) {
		// Add your code here
		for (int i = 0; i<this.entries.size();i++){
			Entry current = this.entries.get(i);
			current.setValue(current.getValue()*scalar);
			this.entries.set(i,current);
		}
	}

	// Number of rows of the matrix
	public int getNumRows() {
		return this.numRows;
	}

	// Number of columns of the matrix
	public int getNumColumns() {
		return this.numCols;
	}

	// Output the elements of the matrix, including the zeros
	// Do not modify this method
	public void print() {
		int n_elem = numRows * numCols;
		int pos = 0;

		for (int i = 0; i < entries.size(); ++i) {
			int nonzero_pos = entries.get(i).getPosition();

			while (pos <= nonzero_pos) {
				if (pos < nonzero_pos) {
					System.out.print("0 ");
				} else {
					System.out.print(entries.get(i).getValue());
					System.out.print(" ");
				}
				if ((pos + 1) % this.numCols == 0) {
					System.out.println();
				}
				pos++;
			}
		}

		while (pos < n_elem) {
			System.out.print("0 ");
			if ((pos + 1) % this.numCols == 0) {
				System.out.println();
			}

			pos++;
		}
	}

//MergeSort
public void merge(ArrayList<Entry> t, ArrayList<Entry> l,
ArrayList<Entry> r, int left, int right) {
		ArrayList<Entry> a = t;
		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
				if (l.get(i).getPosition() <= r.get(j).getPosition()) {
						a.set(k++, l.get(i++)); }
				else {
						a.set(k++, r.get(j++));
				}
		}
		while (i < left) {
				a.set(k++, l.get(i++));
		}
		while (j < right) {
				a.set(k++, r.get(j++));
		}
	}

	public void mergeSort(ArrayList<Entry> a, int n) {
	    if (n < 2) {
	        return;
				}
	    int mid = n / 2;
	    ArrayList<Entry> l = new ArrayList<Entry>();
	    ArrayList<Entry> r = new ArrayList<Entry>();
	    for (int i = 0; i < mid; i++) {
	        l.add(i, a.get(i));
				}
	    for (int i = mid; i < n; i++) {
	        r.add(i - mid, a.get(i));
				}
	    mergeSort(l, mid);
	    mergeSort(r, n - mid);
	    merge(a, l, r, mid, n - mid);
	}

	private int numRows; // Number of rows
	private int numCols; // Number of columns
	private ArrayList<Entry> entries; // Non-zero elements
}
