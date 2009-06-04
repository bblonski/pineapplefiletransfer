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
	
	sprintf(commandBuf, "scp client_info.txt %s:~/", serverAddr);
	int constat;
	constat = system(commandBuf);
	if(constat != 0)
	{
		printf("Unable to Connect to Server\nCheck client_info.txt file, or server config file.\n");
		return(-1);
	}	
	printf("Connection Established.\nAwating Metadata from %s\n", serverName);
	
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
		printf("\nFile Structure has been built sucessfully.\n\n");
	else
	{
		printf("\nConnection Timeout!\n Exiting, Try Again Later.\n\n");
		return(0);
	}	
	
	

	return 0;
}