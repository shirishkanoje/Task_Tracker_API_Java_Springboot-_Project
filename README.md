# Task_Tracker_API_Java_Springboot_Project

## 👤 UserController API Endpoints

### POST /users/register

Request Body Example:

{

  "name": "Shiru",
  
  "email": "shiru@example.com",
  
  "password": "password123"
  
}

Response:
201 Created – User registered successfully.
400 Bad Request – Email already in use.

![Register_User](https://github.com/user-attachments/assets/f1d36819-d2c1-475d-9e62-4271c4e1b4dc)

## ✅ TaskController API Endpoints

### 📝 POST /tasks?userId={userId}

Description:

Creates a new task for a specific user. The userId must be passed as a request parameter.

Request Param:

userId – ID of the user who owns this task

Request Body Example:

{

  "title": "Submit Report",
  
  "description": "Submit final task tracker report to supervisor",
  
  "dueDate": "2025-06-05",
  
  "status": "PENDING"
  
}

Response:

201 Created – Task created and linked to user.

400 Bad Request – User not found.

![Create_Task](https://github.com/user-attachments/assets/d43aad98-00ea-46ef-9ff8-068eee7a97d3)

### GET /tasks

Description:

Fetches a list of all tasks available in the database.

Response:

200 OK – List of all task objects.

![Fetch_All_Tasks](https://github.com/user-attachments/assets/33c6d19c-9d6e-485c-9e65-6fd9e820b494)

### GET /tasks/{id}

Description:

Fetches a specific task by its ID.

id – Task ID

Response:

200 OK – Task found

404 Not Found – Task does not exist

![Get_Specific_Task_By_Id](https://github.com/user-attachments/assets/198d9043-f3b7-4f4e-8108-3b68bc1d2609)

### PUT /tasks/update/{id}

Description:

Updates an existing task's details like title, description, due date, or status.

Path Variable:

id – Task ID

Request Body Example:

{

  "title": "Updated Title",
  
  "description": "Updated Description",
  
  "dueDate": "2025-06-10",
  
  "status": "IN_PROGRESS"
  
  }

Response:

200 OK – Updated task returned

404 Not Found – Task does not exist

![Update_Task](https://github.com/user-attachments/assets/cdae3bec-891e-4ab5-9a0e-cb7443760918)

### ❌ DELETE /tasks/delete/{id}

Description:

Deletes the task with the specified ID.

Path Variable:

id – Task ID

Response:

200 OK – "Task deleted"

404 Not Found – Task not found

![Delete_Task_By_ID](https://github.com/user-attachments/assets/130829a5-5fe1-492a-b5c8-6c5d2d0e3143)

### GET /tasks/status/{status}

Description:

Returns a list of tasks filtered by their status.

Path Variable:

status – Enum value: PENDING, IN_PROGRESS, or COMPLETED

Example:

/tasks/status/COMPLETED

Response:

200 OK – List of tasks matching the status

![Get_Task_By_Status](https://github.com/user-attachments/assets/09c00d47-8c5a-454c-b3bf-751476b08b3d)

### ⏰ GET /tasks/due/{yyyy-MM-dd}

Description:

Returns a list of tasks that are due on the specified date.

Path Variable:

dueDate – Date in yyyy-MM-dd format (e.g., 2025-06-10)

Response:

200 OK – List of tasks due on that date















