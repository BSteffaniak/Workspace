package net.foxycorndog.arrowide.language.assembly;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.Keyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.arrowide.language.Language.ASSEMBLY;
import static net.foxycorndog.arrowide.language.assembly.AssemblyLanguage.KEYWORD_COLOR;

public class AssemblyKeyword
{
	public static Keyword
			INT, MOV, LEA, CALL, SUB, RCX, RSP, RDX, ECX, EAX, DB, DD,
			DQ, JZ, JMP, JE, JNE, INCLUDE, CMP, RAX, SHR, RBX, RSI,
			RDI, ORG, XOR, REP, ADD, RET, EDX, JB;
	
	public static void init()
	{
		INT		= new Keyword(ASSEMBLY, "int", KEYWORD_COLOR);
		MOV		= new Keyword(ASSEMBLY, "mov", KEYWORD_COLOR);
		LEA		= new Keyword(ASSEMBLY, "lea", KEYWORD_COLOR);
		CALL	= new Keyword(ASSEMBLY, "call", KEYWORD_COLOR);
		SUB		= new Keyword(ASSEMBLY, "sub", KEYWORD_COLOR);
		RCX		= new Keyword(ASSEMBLY, "rcx", KEYWORD_COLOR);
		RSP		= new Keyword(ASSEMBLY, "rsp", KEYWORD_COLOR);
		RDX		= new Keyword(ASSEMBLY, "rdx", KEYWORD_COLOR);
		ECX		= new Keyword(ASSEMBLY, "ecx", KEYWORD_COLOR);
		EAX		= new Keyword(ASSEMBLY, "eax", KEYWORD_COLOR);
		DB		= new Keyword(ASSEMBLY, "db", KEYWORD_COLOR);
		DD		= new Keyword(ASSEMBLY, "dd", KEYWORD_COLOR);
		DQ		= new Keyword(ASSEMBLY, "dq", KEYWORD_COLOR);
		JZ		= new Keyword(ASSEMBLY, "jz", KEYWORD_COLOR);
		JMP		= new Keyword(ASSEMBLY, "jmp", KEYWORD_COLOR);
		JE		= new Keyword(ASSEMBLY, "je", KEYWORD_COLOR);
		JNE		= new Keyword(ASSEMBLY, "jne", KEYWORD_COLOR);
		INCLUDE	= new Keyword(ASSEMBLY, "include", KEYWORD_COLOR);
		CMP		= new Keyword(ASSEMBLY, "cmp", KEYWORD_COLOR);
		RAX		= new Keyword(ASSEMBLY, "rax", KEYWORD_COLOR);
		SHR		= new Keyword(ASSEMBLY, "shr", KEYWORD_COLOR);
		RBX		= new Keyword(ASSEMBLY, "rbx", KEYWORD_COLOR);
		RSI		= new Keyword(ASSEMBLY, "rsi", KEYWORD_COLOR);
		RDI		= new Keyword(ASSEMBLY, "rdi", KEYWORD_COLOR);
		ORG		= new Keyword(ASSEMBLY, "org", KEYWORD_COLOR);
		XOR		= new Keyword(ASSEMBLY, "xor", KEYWORD_COLOR);
		REP		= new Keyword(ASSEMBLY, "rep", KEYWORD_COLOR);
		ADD		= new Keyword(ASSEMBLY, "add", KEYWORD_COLOR);
		RET		= new Keyword(ASSEMBLY, "ret", KEYWORD_COLOR);
		EDX		= new Keyword(ASSEMBLY, "edx", KEYWORD_COLOR);
		JB		= new Keyword(ASSEMBLY, "jb", KEYWORD_COLOR);
	}
}