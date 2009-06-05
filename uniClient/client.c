/*
 *  client.c
 *  
 *
 *  Created by Matthew Schlachtman on 6/4/09.
 *  Copyright 2009 Cal Poly San Luis Obispo. All rights reserved.
 *
 */

#include "client.h"

node* HEAD = NULL;
char* serverName;
char* serverAddr;
char newline;

long getSize(){
	if(HEAD == NULL)
		return 0;
	long size = 0;
	
	node* temp = HEAD;
	while(temp->next != NULL)
	{
		size += temp->size;
		temp = temp->next;
	}
	
	return size;
}

void addEntry(char* name, long size, int numUse){
	//checkCache(name);
	
	//printf("made it into add function.\n");
	
    node *e = (node*)malloc(sizeof(node));
	//printf("made itpast malloc of node\n");
	strcpy(e->fname,name);
	//printf("made it past str copy\n");
	e->size = size;
	e->numUsed = numUse;
	e->lastAccessed = time(NULL);
	
	if(size >= CACHE_MAX) {
		//ERROR (TODO Make print a message to iPhone or something)
	}
	
	long cacheSize = getSize(HEAD);
	while(cacheSize + size > CACHE_MAX){
		createSpace();
	}
	
	e->next = HEAD;
	
	HEAD = e;
	//return(HEAD);
}


void deleteEntry(char* name){
	//printf("made it into delete function.\n");
	
	node* temp = HEAD;
	
	if(strcmp(temp->fname, name) == 0){
		HEAD = temp->next;
		free(temp);
		//return true;		
	}
	node* prev = temp;
	temp = temp->next;
	
	while(temp->next != NULL){
		if(strcmp(temp->fname, name) == 0){
			prev->next = temp->next;
			free(temp);
			//return true;
		}
		prev = temp;
		temp = temp->next;
	}
	//return false; //BABY!
	//return(HEAD);
}

int checkCache(char* name) 
{
	//printf("made it into check function.\n");
	if(HEAD == NULL)
		return 0;
	node* itr = HEAD;
	while(itr->next != NULL) {
		if(strcmp(itr->fname, name) == 0) {
			//deleteEntry(itr->fname, itr->size);
         if(itr->numUsed != 0) {
			   return 1;
         }
         else {
            return 0;
         }
		}
		itr = itr->next;
	}
	
	return 0;
}

int updateEntry(char* name)
{
   if (HEAD == NULL)
      return 0;

   node* itr = HEAD;
   while (itr->next != NULL) {
      if (strcp(itr->fname, name) == 0) {
         itr->numUsed = itr->numUsed + 1;
         itr->lastAccessed = time(NULL);
         return 1;
      }
      itr = itr->next;
   }

   return 0;
}

void createSpace()
{
	node* temp = HEAD;
	node* candidate = HEAD;
	
	while(temp->next != NULL){
		temp = temp->next;
		if(candidate->numUsed > temp->numUsed){
			candidate = temp;
		}else if(candidate->numUsed == temp->numUsed){
			if(candidate->lastAccessed > temp->lastAccessed){
				candidate = temp;
			}
		}
	}
	
	deleteEntry(candidate->fname);
}	

void printCache()
{
	printf("***********Start******************\n");
	node* tmp = HEAD;
	while(tmp != NULL)
	{
		printf("%s %ld %d\n", tmp->fname, tmp->size, tmp->numUsed);
		tmp = tmp->next;
	}
	printf("***********END********************\n");
}	

int fsint(char* argv)
{
	FILE* metadata;
	int fileds;
	char itype;
	char* filename;
	filename = (char*)malloc(200);
	int fsize;
	char* delete;
	delete = (char*)malloc(204);
	char* directories;
	directories = (char*)malloc(204);
	char* serverip;
	serverip = (char*)malloc(20);
	char* realaddr;
	realaddr = (char*)malloc(220);
	
	
	metadata = fopen("metadata.wtf", "r");
	if(metadata == NULL)
	{
		//printf(".");
		return(-1);
	}
	
	fscanf(metadata, "%s%c",serverip, &newline); 
	while(1)
	{	
		fscanf(metadata,"%c%s%d%c", &itype, filename, &fsize, &newline);
		
		//printf("The itypye = %c\n", itype);
		//printf("The filename is = %s\n", filename);
		//printf("the fsize is = %d\n", fsize);
		
		sprintf(realaddr, "%s/%s", argv, filename);
		
		if(itype == '+')
		{
			
			fileds = open(realaddr, O_CREAT | O_WRONLY | O_TRUNC, 0600);
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
				sprintf(directories, "mkdir -p %s", realaddr);
				//printf("%s\n", directories);
				system(directories);
				filename[lastslash] = '/';
				fileds = open(realaddr, O_CREAT | O_WRONLY | O_TRUNC, 0600);
			}	
			close(fileds);
			if(checkCache(filename) == 1)
				deleteEntry(filename);
			addEntry(filename, fsize, 0);
		}	
		else if(itype == '-')
		{
			sprintf(delete, "rm -r %s%c", realaddr, '\0');
			if(checkCache(filename) == 1)
				deleteEntry(filename);
			system(delete);
		}	
		else
			break;
	}
	
	fclose(metadata);
	system("rm metadata.wtf");
	printCache(HEAD);
	return(0);
}	

int main(int argc, char** argv)
{
	char* config;
	config = (char*)malloc(200);
	serverName = (char*)malloc(200);
	serverAddr = (char*)malloc(200);
	FILE* configfile;
	char newline;
	char* commandBuf;
	commandBuf = (char*)malloc(400);
	
	//node* HEAD = NULL;
	
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
	
	//sprintf(commandBuf, "cd %s", serverName);
	//system(commandBuf);
	
	//sprintf(commandBuf, "./fsint %s", serverName);
	
	int status;
	int i;
	for(i = 0; i < 10; i++)
	{
		status = fsint(serverName);
		if(status == 0)
			break;
		else
			sleep(6);
	}
	if(status == 0)
	{
		system("clear");
		printf("\nFile Structure for %s has been built/updated sucessfully.\n\n", serverName);
	}
	else
	{
		printf("\nConnection Timeout!\n Exiting, Try Again Later.\n\n");
		return(0);
	}	
	
	
	printf("Using another terminal or file browser you\n are welcome to explore the %s folder.\n", serverName);
	menuSel();	
	
	return 0;
}

void menuSel()
{
	char* choice;
   char* filename;
	int rval;
	choice = (char*)malloc(100);
   filename = (char*)malloc(256);
	printf("Enter a Command(update, view, open, quit): ");
	scanf("%s%c", choice, &newline);
	if(strcmp("quit", choice) == 0)
	{
		system("clear");
		printf("Thank You for using Pineapple Ubiquitous Data Access!\n");
		exit(0);
	}
	else if(strcmp("update", choice) == 0)
	{
		rval = fsint(serverName);
		if(rval != 0)
			printf("No New Metadata.\n");
		menuSel();
	}
	else if(strcmp("view", choice) == 0)
	{
		printCache();
		menuSel();
	}
	else if(strcmp("open", choice) == 0)
	{
      printf("Enter the full path of the file you wish to open: ");
      scanf("%s%c", filename, &newline);
		request(filename);
		menuSel();
	}
	else
	{
		printf("Invalid Request!\n");
		menuSel();
	}	
	
}

void request(char* filename)
{
   char* commandBuf;
	commandBuf = (char*)malloc(400);

   if (checkCache(filename) == 1) {
      // File is in cache and stored in memory
      if(updateEntry(filename) == 1) {
         // View the file
         sprintf(commandBuf, "less %s", filename);
	      system(commandBuf);
      }
      else {
         printf("ERROR: %s should exist in cache!", name);
      }
   }
   else {
      // File should be in cache with numUsed == 0
      if(updateEntry(filename) == 1) { // Updated numUsed and lastAccessed
         // Download file
         sprintf(commandBuf, "scp %s:~/ %s", serverAddr, filename);
	      int constat;
	      constat = system(commandBuf);
	      if(constat != 0)
	      {
		      printf("Unable to Connect to Server\nCheck client_info.txt file, or server config file.\n");
         }
      }
      else {
         printf("ERROR: %s does not exist in file structure!", name);
      }
   }
}

