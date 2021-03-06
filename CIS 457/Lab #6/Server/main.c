#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <pthread.h>

#define MESSAGE_SIZE 2048

int queryPort() {
    printf("Enter the server port number: ");
    char port[5000] = "8080";
    fgets(port, 5000, stdin);
    return atoi(port);
}

char* queryInput() {
//    printf("Type something to the client: ");
    char* input = (char*) malloc(sizeof(char));
    fgets(input, MESSAGE_SIZE, stdin);
    input[strlen(input) - 1] = '\0';
    return input;
}

void* receiveMsg(void* sock) {
    int socketFileDescriptor = *(int*)sock;
    char response[MESSAGE_SIZE];
    while (1) {

        // Deals with receiving from the server
        recv(socketFileDescriptor, response, MESSAGE_SIZE, 0);

        if (strcmp("Quit", response) == 0) {
            printf("Server has disconnected. Closing client.\n");
            return NULL;
        } else {
            printf("Client: %s\n", response);
        }
    }
}

int main(int argc, char** argv) {
    int socketFileDescriptor = socket(AF_INET, SOCK_STREAM, 0);

    if (socketFileDescriptor < 0) {
        printf("There was an error creating the socket.\n");
        return 1;
    }

    uint16_t port = (uint16_t) queryPort();

    printf("Server listening on 127.0.0.1:%d\n", port);

    struct sockaddr_in serverAddressInfo, clientAddressInfo;

    serverAddressInfo.sin_family = AF_INET;
    serverAddressInfo.sin_port = htons(port);
    serverAddressInfo.sin_addr.s_addr = INADDR_ANY;

    int socketBind = bind(socketFileDescriptor, (struct sockaddr*)&serverAddressInfo, sizeof(serverAddressInfo));

    if (socketBind < 0) {
        printf("Bind error.\n");
        return 3;
    }

    listen(socketFileDescriptor, 10);

    char response[MESSAGE_SIZE];
    char* input;

    int length = sizeof(clientAddressInfo);
    int clientSocket = accept(socketFileDescriptor, (struct sockaddr*)&clientAddressInfo, &length);

    pthread_t thread;

    int receiveResult = pthread_create(&thread, NULL, receiveMsg, socketFileDescriptor);
    if (receiveResult < 0) {
        printf("There was an error creating the receive thread.\n");
    }

    pthread_detach(thread);

    while (1) {
        input = queryInput();
        send(clientSocket, input, strlen(input) + 1, 0);

        if (strcmp("Quit", input) == 0) {
            close(clientSocket);
            close(socketFileDescriptor);
            break;
        }
    }

    return 0;
}