# QuickChat Application - Portfolio of Evidence (PoE)

## 📱 Project Overview

QuickChat is a Java-based chat application that demonstrates fundamental programming concepts including OOP principles, data validation, message handling, and automated testing. This project is designed to teach best practices in software development including version control, unit testing, and CI/CD automation.

## 🎯 Learning Objectives

By completing this project, you will demonstrate proficiency in:
- **Object-Oriented Programming**: Classes, methods, interfaces
- **Data Types & Variables**: Variable scope and data manipulation
- **Control Structures**: Decisions, loops, and operators
- **Arrays & Collections**: Data storage and manipulation
- **String Handling**: Text processing and validation
- **Testing**: Unit tests with JUnit
- **Version Control**: Git and GitHub workflows
- **CI/CD**: Automated testing with GitHub Actions

## 🏗️ Project Structure

The application consists of three main parts:

### Part 1: Registration and Login Feature (45 marks)
- User registration with validation
- Secure login system
- Input validation for username, password, and phone number

### Part 2: Sending Messages (65 marks)
- Message creation and management
- Message validation and formatting
- JSON storage functionality
- Interactive menu system

### Part 3: Data Storage and Reporting (85 marks)
- Array-based data management
- Message search and filtering
- Report generation
- Message deletion functionality

## 📋 Requirements Summary

### Prerequisites
1. **GitHub Account** - Use your student email
2. **GitHub Student Developer Pack** - Apply at https://education.github.com/pack
3. **Java Development Environment** - NetBeans or similar IDE
4. **JUnit Testing Framework** - For automated testing

### Core Features

#### 🔐 Authentication System
- **Username Validation**: Must contain underscore (_) and be ≤5 characters
- **Password Complexity**: ≥8 characters, uppercase, number, special character
- **Phone Number Validation**: International format (+27) with ≤10 digits total
- **Login Verification**: Secure authentication with personalized welcome

#### 💬 Messaging System
- **Message Creation**: Text messages up to 250 characters
- **Message ID**: Auto-generated 10-digit unique identifier
- **Message Hash**: Format: `XX:Y:FIRSTLAST` (first 2 digits of ID : message number : first and last words)
- **Recipient Validation**: International phone number format
- **Message Actions**: Send, Store (JSON), or Disregard
- **Message Display**: Full details via JOptionPane

#### 📊 Data Management
- **Arrays for Storage**:
  - Sent Messages
  - Disregarded Messages  
  - Stored Messages (from JSON)
  - Message Hashes
  - Message IDs
- **Search Functionality**:
  - Find by Message ID
  - Filter by recipient
  - Display longest message
- **Report Generation**: Complete message details
- **Message Deletion**: By message hash

## 🧪 Testing Requirements

### Unit Tests (JUnit)
All methods must have corresponding unit tests with specific test data:

#### Authentication Tests
- Username validation (valid: "kyl_1", invalid: "kyle!!!!!!")
- Password complexity (valid: "Ch&&sec@ke99!", invalid: "password")
- Phone validation (valid: "+27838968976", invalid: "08966553")

#### Message Tests
- Message length validation (≤250 characters)
- Message hash generation
- Message ID creation
- Send/Store/Disregard functionality

#### Data Management Tests
- Array population verification
- Search functionality validation
- Report generation testing
- Message deletion confirmation

## 🚀 Development Workflow

### 1. Setup Phase
```bash
# Create GitHub repository
# Clone to local environment
# Set up project structure
```

### 2. Development Phases
```bash
# Create feature branch
git checkout -b feature-authentication

# Develop and test
# Commit changes
git add .
git commit -m "Add authentication system"

# Push to GitHub
git push origin feature-authentication
```

### 3. Testing & CI/CD
- Write unit tests for all methods
- Set up GitHub Actions for automated testing
- Ensure all tests pass before merging

## 📁 Project Structure
```
QuickChat/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Login.java
│   │       ├── Message.java
│   │       └── QuickChatApp.java
│   └── test/
│       └── java/
│           ├── LoginTest.java
│           └── MessageTest.java
├── data/
│   └── stored_messages.json
├── .github/
│   └── workflows/
│       └── ci.yml
├── README.md
└── pom.xml (or build.gradle)
```

## 🔧 Technical Specifications

### Login Class Methods
- `Boolean checkUserName()` - Username validation
- `Boolean checkPasswordComplexity()` - Password validation  
- `Boolean checkCellPhoneNumber()` - Phone validation
- `String registerUser()` - Registration process
- `Boolean loginUser()` - Authentication
- `String returnLoginStatus()` - Login messaging

### Message Class Methods
- `Boolean checkMessageID()` - ID validation
- `Int checkRecipientCell()` - Recipient validation
- `String createMessageHash()` - Hash generation
- `String sentMessage()` - Message actions
- `String printMessages()` - Message listing
- `Int returnTotalMessages()` - Message counting
- `void storeMessage()` - JSON storage (AI-assisted)

## 📝 Test Data

### Sample Registration Data
- **Valid Username**: "kyl_1"
- **Valid Password**: "Ch&&sec@ke99!"
- **Valid Phone**: "+27838968976"

### Sample Message Data
1. **Message 1**: "+27834557896" - "Did you get the cake?" (Sent)
2. **Message 2**: "+278388884567" - "Where are you? You are late! I have asked you to be on time." (Stored)
3. **Message 3**: "+27834484567" - "Yohoooo, I am at your gate." (Disregard)
4. **Message 4**: "0838884567" - "It is dinner time !" (Sent)
5. **Message 5**: "+278388884567" - "Ok, I am leaving without you." (Stored)

## 🤖 AI Tool Usage

This project encourages responsible use of AI tools (ChatGPT, Claude, Copilot) for:
- Regular expression development for phone validation
- JSON storage implementation
- Code optimization and debugging

**Important**: All AI-generated code must be properly attributed using APA format.

## 📚 Resources

- [JUnit Testing Tutorial](https://www.youtube.com/playlist?list=PL480DYS-b_kHSYf2yzLgto_mwDr_U-Q6)
- [GitHub Actions Setup](https://www.youtube.com/watch?v=b3cIRsVPLR4&t=282s)
- [Chat App Architecture Guide](https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/)
- [APA Citation for AI Tools](https://apastyle.apa.org/blog/how-to-cite-chatgpt)

## 🎯 Success Criteria

- ✅ All unit tests pass
- ✅ Code follows OOP principles
- ✅ Proper error handling and validation
- ✅ Clean, maintainable code structure
- ✅ Automated testing pipeline
- ✅ Proper version control usage
- ✅ Complete documentation

## 🚦 Getting Started

1. **Fork/Clone** this repository
2. **Set up** your development environment
3. **Create** feature branches for each part
4. **Develop** incrementally with tests
5. **Document** your progress
6. **Submit** via GitHub with all tests passing

---

**Note**: This is an educational project designed to teach software engineering best practices. Focus on code quality, testing, and proper development workflows rather than just functionality. 