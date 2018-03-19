package sdc.fixtures.base;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface CsvProcessor<T> {
	public CellProcessor[] getProcessors();
	
	public void process(T data);
}
