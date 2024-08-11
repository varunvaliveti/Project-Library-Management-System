# CS151 Final Project: Library Management System

## Description

The Library Management System is a small application designed to manage a library's books and users. It allows users to check out and return books, while an admin user has additional privileges to deactivate and activate users. The system state is serialized, ensuring that data is preserved beyond runtime. The library comes preloaded with a set of books and users to get started quickly.

## Features

- **User Management**: Create new users, check out books, and return them.
- **Admin Capabilities**: Admins can manage user accounts, including activating and deactivating users.
- **Persistent State**: The system's state, including users and books, is serialized, so data is not lost between sessions.
- **Preloaded Data**: The library comes with a set of preloaded books and users.

## Getting Started

To start using the Library Management System, follow these steps:

### 1. Compile the Java Files

```bash
javac Library/*.java
```

### 2. Run program

```bash
java Library.WelcomeScreen
```
## Authors
- Aneesh Ramanathan
- Varun Valiveti
- Andy Van
