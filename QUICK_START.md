# QuickChat Application - Quick Start Guide

**AI Attribution**: This technical guide was developed with assistance from Claude AI (Anthropic, 2024) to provide comprehensive setup instructions and troubleshooting guidance.

## 🚀 Prerequisites

Before running the QuickChat application, ensure you have the following installed:

### **Required Software**
- **Java Development Kit (JDK) 11 or higher**
  - Download from: https://adoptium.net/
  - Verify installation: `java -version` and `javac -version`

- **Apache Maven 3.6+**
  - Download from: https://maven.apache.org/download.cgi
  - Verify installation: `mvn -version`

- **Git**
  - Download from: https://git-scm.com/downloads
  - Verify installation: `git --version`

### **Optional but Recommended**
- **IDE**: IntelliJ IDEA, Eclipse, or NetBeans
- **GitHub Account**: For version control and CI/CD features

## 📥 Installation

### **1. Clone the Repository**
```bash
# Clone the project
git clone <repository-url>
cd QuickChat

# Or if you have the project locally
cd path/to/QuickChat
```

### **2. Verify Project Structure**
```
QuickChat/
├── src/main/java/
│   ├── Login.java
│   ├── Message.java
│   └── QuickChatApp.java
├── src/test/java/
│   ├── LoginTest.java
│   ├── MessageTest.java
│   └── MessagePhase3Test.java
├── pom.xml
└── data/
```

### **3. Install Dependencies**
```bash
# Download and install all Maven dependencies
mvn clean install
```

## ▶️ Running the Application

### **Method 1: Using Maven (Recommended)**
```bash
# Compile and run the application
mvn clean compile exec:java -Dexec.mainClass="QuickChatApp"
```

### **Method 2: Using Java directly**
```bash
# Compile the source code
mvn clean compile

# Run the main class
java -cp target/classes QuickChatApp
```

### **Method 3: Using IDE**
1. Open the project in your IDE
2. Navigate to `src/main/java/QuickChatApp.java`
3. Right-click and select "Run" or use the IDE's run button

## 🧪 Running Tests

### **Run All Tests**
```bash
# Execute all unit tests
mvn test
```

### **Run Specific Test Classes**
```bash
# Run only Login tests
mvn test -Dtest=LoginTest

# Run only Message tests
mvn test -Dtest=MessageTest

# Run only Phase 3 tests
mvn test -Dtest=MessagePhase3Test
```

### **Generate Test Reports**
```bash
# Generate detailed test reports
mvn surefire-report:report

# View reports at: target/site/surefire-report.html
```

### **Code Coverage Analysis**
```bash
# Generate code coverage report
mvn clean test jacoco:report

# View coverage at: target/site/jacoco/index.html
```

## 🎮 Using the Application

### **1. Application Startup**
When you run the application, you'll see:
```
Welcome to QuickChat

Please choose an option:
1) Send Messages
2) Show recently sent messages
3) Quit
```

### **2. User Registration & Login**
Before sending messages, you'll need to:
1. **Register** with valid credentials:
   - Username: Must contain underscore (_) and be ≤5 characters
   - Password: ≥8 characters, uppercase, number, special character
   - Phone: South African format (+27xxxxxxxxx)

2. **Login** with your registered credentials

### **3. Sending Messages**
1. Choose option 1 from the main menu
2. Specify how many messages you want to send
3. For each message:
   - Enter recipient phone number (+27xxxxxxxxx format)
   - Enter message text (≤250 characters)
   - Choose action: Send, Store, or Disregard

### **4. Message Management**
- **View Messages**: See all sent messages with details
- **Search**: Find messages by ID or recipient
- **Reports**: Generate comprehensive message reports
- **Delete**: Remove messages using message hash

## 🔧 Configuration

### **JSON Storage**
- Stored messages are saved to: `data/stored_messages.json`
- The data directory is created automatically
- JSON files are human-readable and can be inspected

### **Test Data**
The application includes predefined test data for validation:
- **Valid Username**: "kyl_1"
- **Valid Password**: "Ch&&sec@ke99!"
- **Valid Phone**: "+27838968976"

## 🐛 Troubleshooting

### **Common Issues**

#### **"Command not found" errors**
```bash
# Check if Java is installed
java -version

# Check if Maven is installed
mvn -version

# Add to PATH if necessary (Windows)
set PATH=%PATH%;C:\path\to\java\bin;C:\path\to\maven\bin

# Add to PATH if necessary (Mac/Linux)
export PATH=$PATH:/path/to/java/bin:/path/to/maven/bin
```

#### **Compilation Errors**
```bash
# Clean and rebuild
mvn clean compile

# If dependencies are missing
mvn dependency:resolve
```

#### **Test Failures**
```bash
# Run tests with verbose output
mvn test -X

# Run specific failing test
mvn test -Dtest=ClassName#methodName
```

#### **Port/Permission Issues**
- Ensure no other applications are using required resources
- Run with appropriate permissions
- Check firewall settings if applicable

### **Memory Issues**
```bash
# Increase Maven memory if needed
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
```

## 📊 Performance Tips

### **Optimal Running Conditions**
- **RAM**: Minimum 512MB available
- **Java Heap**: Default settings are sufficient
- **Disk Space**: ~50MB for dependencies and build artifacts

### **IDE Configuration**
- **IntelliJ IDEA**: Import as Maven project
- **Eclipse**: Use "Import Existing Maven Projects"
- **NetBeans**: Open as Maven project

## 🔍 Verification

### **Verify Installation Success**
```bash
# 1. All tests should pass
mvn test
# Expected: Tests run: 57, Failures: 0, Errors: 0, Skipped: 0

# 2. Application should compile without errors
mvn clean compile
# Expected: BUILD SUCCESS

# 3. Application should run
mvn exec:java -Dexec.mainClass="QuickChatApp"
# Expected: Welcome to QuickChat menu appears
```

### **Health Check Commands**
```bash
# Check project structure
ls -la src/main/java/
ls -la src/test/java/

# Verify dependencies
mvn dependency:tree

# Check for any issues
mvn validate
```

## 📚 Additional Resources

### **Documentation**
- **JavaDoc**: Generate with `mvn javadoc:javadoc`
- **Project Reports**: Generate with `mvn site`

### **Development Tools**
- **Maven Wrapper**: Use `./mvnw` instead of `mvn` for consistent versions
- **IDE Plugins**: Install Maven and Git plugins for better integration

### **Useful Maven Commands**
```bash
# Clean build artifacts
mvn clean

# Compile without running tests
mvn compile -DskipTests

# Package into JAR
mvn package

# Install to local repository
mvn install

# Run in debug mode
mvn exec:java -Dexec.mainClass="QuickChatApp" -Dexec.args="-Xdebug"
```

## 🆘 Getting Help

### **If You Encounter Issues**
1. **Check Prerequisites**: Ensure all required software is installed
2. **Verify Versions**: Use compatible Java and Maven versions
3. **Clean Build**: Run `mvn clean install` to reset
4. **Check Logs**: Look for error messages in console output
5. **Test Environment**: Try running on a different machine/environment

### **Support Resources**
- **Maven Documentation**: https://maven.apache.org/guides/
- **JUnit 5 Guide**: https://junit.org/junit5/docs/current/user-guide/
- **Java Documentation**: https://docs.oracle.com/en/java/

---

**Quick Start Complete!** 🎉  
Your QuickChat application should now be running successfully. 