/*
 *  menu.c
 *  
 *  Copyright 2009 Cal Poly San Luis Obispo. All rights reserved.
 *
 */

#include "menu.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char** argv)
{
	char* config;
	config = (char*)malloc(200);
	char* serverName;
	serverName = (char*)malloc(200);
	char* serverAddr;
	serverAddr = (char*)malloc(200);
	FILE* configfile;
	char newline;
	char* commandBuf;
	commandBuf = (char*)malloc(200);

	system("clear");
	printf("Welcome to Pineapple Ubiquitous Data Access\n");
	printf("Please enter the server config file: ");
	scanf("%s%c", config, &newline);
	
	configfile = fopen(config, "r");
	if(configfile == NULL)
	{
		printf("Error Unable to Open Server Config file.\n");
		return(-1);
	}
	
	fscanf(configfile, "%s%s", serverName, serverAddr);
	
	sprintf(commandBuf, "mkdir %s", serverName);
	system(commandBuf);
	sprintf(commandBuf, "cp -f fsint ./%s/", serverName);
	system(commandBuf);
	//Insert other modules
	
	sprintf(commandBuf, "scp client_info.txt %s", serverAddr);
	system(commandBuf);
	printf("Awating Metadata from %s\n", serverName);
	
	sprintf(commandBuf, "cd %s", serverName);
	system(commandBuf);
	
	int status;
	int i;
	for(i = 0; i < 10; i++)
	{
		status = system("./fsint");
		if(status == 0)
			break;
		else
			sleep(6);
	}
	if(status == 0)
		printf("File Structure has been built sucessfully.\n");
	else
	{
		printf("Connection Timeout!\n Exiting, Try Again Later.\n");
		return(0);
	}	
	
	

	return 0;
}