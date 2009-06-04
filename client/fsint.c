/*
 *  fsint.c
 *  
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
	FILE* metadata;
	int fileds;
	char newline;
	char itype;
	char* filename;
	filename = (char*)malloc(200);
	int fsize;
	char* delete;
	delete = (char*)malloc(204);
	char* directories;
	directories = (char*)malloc(204);
	
	metadata = fopen("metadata.wtf", "r");
	if(metadata == NULL)
	{
		printf(".");
		return(-1);
	}
	
	while(1)
	{	
		fscanf(metadata,"%c%s%d%c", &itype, filename, &fsize, &newline);
		
		//printf("The itypye = %c\n", itype);
		//printf("The filename is = %s\n", filename);
		//printf("the fsize is = %d\n", fsize);
		
		if(itype == '+')
		{
			
			fileds = open(filename, O_CREAT | O_WRONLY | O_TRUNC, 0600);
			if(fileds == -1)
			{
				//printf("The filename in the mkdir condition = %s\n", filename);
				int lastslash = 0;
				int i;
				for(i = 0; filename[i] != '\0'; i++)
				{
					//printf("this character is %c %d\n", filename[i], (int)filename[i]);
					if(filename[i] == '/')
					{	
						lastslash = i;
					}	
				}
				fflush(stdout);
				filename[lastslash] = '\0';
				sprintf(directories, "mkdir -p %s", filename);
				//printf("%s\n", directories);
				system(directories);
				filename[lastslash] = '/';
				fileds = open(filename, O_CREAT | O_WRONLY | O_TRUNC, 0600);
			}	
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
	
	fclose(metadata);
	system("rm metadata.wtf");
	return 0;
}	
	
