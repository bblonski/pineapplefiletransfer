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
	
	printf("Welcome to Pineapple Ubiquitous Data Access\n");
	printf("Please enter the server config file: ");
	scanf("%s%c", config, &newline);
	
	configfile = fopen(config);
	if(configfile == NULL)
	{
		printf("Error Unable to Open Server Config file."\n);
		return(-1);
	}
	
	fscanf(configfile, "%s%s", serverName, serverAddr);
	
	sprintf(commandBuf, "mkdir %s", serverName);
	system(commandBuf);
	sprintf(commandBuf, "cp -f fsint ./%s/", serverName);
	system(commandBuf);
	//Insert other modules
	
	sprintf(commandBuf, "scp client_info.txt %s", serverAddr);
	
	printf("Attempting to connect with %s\n", serverName);
	//incomplete
	

	return 0;
}