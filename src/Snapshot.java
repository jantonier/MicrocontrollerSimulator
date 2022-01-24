
public class Snapshot {

	String [] memory = new String[4096];//<<<<<<<
	String [] realmemory = new String[2048];
	String [] register = new String[8];
	String instructionRegister;
	String stackPointer;//HAY QUE INICIALIZAR EL SP
	int pc;
	String stringToDisplay;
	String t1, t2, t3, t4, temp;
	String cond;
	
	public Snapshot(String[] memory, String[] register, String instructionRegister, 
			String stackPointer, int pc, String stringToDisplay, String cond) {
		for(int i = 0; i < 4096; i++)
			this.memory[i] = memory[i];

		for (int i=0; i<2048;i++){
			realmemory[i] = memory[i] + memory[i+1];
		}

		for(int i = 0; i < 8; i++)
			this.register[i] = register[i];
		this.instructionRegister = instructionRegister;
		this.stackPointer = stackPointer;
		this.pc = pc;
		this.stringToDisplay = stringToDisplay;
		this.cond = cond;

	}

	public String[] getMemory() {
		return this.memory;
	}

	public void setMemory(String[] memory) {
		this.memory = memory;
	}

	public String[] getRegister() {
		return register;
	}

	public void setRegister(String[] register) {
		this.register = register;
	}

	public String getInstructionRegister() {
		return instructionRegister;
	}

	public void setInstructionRegister(String instructionRegister) {
		this.instructionRegister = instructionRegister;
	}

	public String getStackPointer() {
		return stackPointer;
	}

	public void setStackPointer(String stackPointer) {
		this.stackPointer = stackPointer;
	}

	public String getPc() {
		return Integer.toString(pc);
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public String getStringToDisplay() {
		return stringToDisplay;
	}

	public void setStringToDisplay(String stringToDisplay) {
		this.stringToDisplay = stringToDisplay;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
	}

	


}
