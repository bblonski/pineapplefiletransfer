#include <time.h>


//Authors: Jon and Greg

#define CACHE_MAX 1000



struct
   {
       char fname[256];
       long size;   
       int numUsed;
       time_t lastAccessed;
   } entry;


struct
   {
	entry e;
	struct node* next;
   } node;


node* HEAD = NULL;

//Gets the current cache size
long getSize(){
	long size = 0;
	
	node* temp = HEAD;
	while(temp.next != NULL){
		size += temp->e.size;
		temp = temp.next;
	}

	return size;
}

void addEntry(char* name, long size){
	entry* e = (entry *)malloc(sizeof(entry));
	strcpy(e->fname,name);
	e->size = size;
	e->numUsed = 1;
	e->lastAccessed = time(NULL);

	if(size >= CACHE_MAX) {
		//ERROR (TODO Make print a message to iPhone or something)
	}

	long cacheSize = getSize();
	while(cacheSize + size > CACHE_MAX){
		createSpace();
	}
		
	node* temp = (node*) malloc(sizeof(node));
	temp->e = e;
	temp->next = HEAD;

	HEAD = temp;
}


bool deleteEntry(char* name, long size){
	node* temp = HEAD;

	if(strcmp(temp->e.fname, name) == 0 && temp->e.size == size){
		HEAD = temp->next;
		free(temp);
		return true;		
	}
	node* prev = temp;
	temp = temp->next;

	while(temp->next != NULL){
		if(strcmp(temp->e.fname, name) == 0 && temp->e.size == size){
			prev->next = temp->next;
			free(temp);
			return true;
		}
		prev = temp;
		temp = temp->next;
	}
	return false; //BABY!
}

void createSpace(){
	node* temp = HEAD;
	node* candidate = HEAD;
	
	while(temp->next != NULL){
		temp = temp->next;
		if(candidate->e.numUsed > temp->e.numUsed){
			candidate = temp;
		}else if(candidate->e.numUsed == temp->e.numUsed){
			if(candidate->e.lastAccessed > temp->e.lastAccessed){
				candidate = temp;
			}
		}
	}

	deleteEntry(candidate->e.fname,candidate->e.size);
}	








