package javabase.proxy.stt;

public class SubjectProxy implements Subject {
	Subject subject;

	public SubjectProxy(Subject subject){
		this.subject = subject;
	}
	
	@Override
	public void operate() {
		System.out.println(" proxy opetation before real operation");
		subject.operate();
		System.out.println(" proxy operation after real operation"); 
	}

}
