/*
 *  client.h
 *  
 *
 *  Created by Matthew Schlachtman on 6/4/09.
 *  Copyright 2009 Cal Poly San Luis Obispo. All rights reserved.
 *
 */

#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>

#define CACHE_MAX 1000000

struct list_el
{
    char fname[256];
    long size;   
    int numUsed;
    time_t lastAccessed;
	struct list_el *next;
};

typedef struct list_el node;


//Gets the current cache size
long getSize();

void addEntry(char* name, long size, int numUse);		

void deleteEntry(char* name);

int checkCache(char* name);

void createSpace();

void printCache();

int fsint(char* argv);

int main(int argc, char** argv);

