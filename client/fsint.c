/*
 *  fsint.c
 *  
 *
 *  This program makes or removes files from the fs
 *  Currently there are issues with making the directory, and the input
 *  comes from stdin but can be redirected ie. ./fsint < buildTest
 *  This test makes 4 test files and dletes the second one.
 *  Copyright 2009 Cal Poly San Luis Obispo. All rights reserved.
 *
 */

#include "fsint.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char** argv)
{
	int fileds;
	char newline;
	char itype;
	char* filename;
	filename = (char*)malloc(200);
	int fsize;
	char* delete;
	delete = (char*)malloc(204);
	
	while(1)
	{	
		scanf("%c%s%d%c", &itype, filename, &fsize, &newline);
		
		//printf("The itypye = %c\n", itype);
		//printf("The filename is = %s\n", filename);
		//printf("the fsize is = %d\n", fsize);
		
		if(itype == '+')
		{		
			fileds = open(filename, O_CREAT | O_WRONLY | O_TRUNC, 0600);
			close(fileds);
		}	
		else if(itype == '-')
		{
			sprintf(delete, "rm %s%c", filename, '\0');
			system(delete);
		}	
		else
			break;
	}
	return 0;
}	
	
