pipeline {
    agent any
    
    environment {
        // Application configuration
        APP_NAME = 'spring-oracle'
        APP_VERSION = "${BUILD_NUMBER}"
        DOCKER_IMAGE = "${APP_NAME}:${APP_VERSION}"
        DOCKER_REGISTRY = 'your-registry.com' // Replace with your Docker registry
        DOCKER_REPO = "${DOCKER_REGISTRY}/${APP_NAME}"
        
        // Database configuration for testing
        DB_HOST = 'oracle-db'
        DB_PORT = '1521'
        DB_SERVICE = 'XE'
        DB_USERNAME = 'demo'
        DB_PASSWORD = 'demo_password'
        
        // Maven configuration
        MAVEN_OPTS = '-Xmx1024m -XX:MaxPermSize=256m'
    }
    
    tools {
        maven 'Maven-3.9.6'
        jdk 'JDK-21'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    env.GIT_COMMIT_SHORT = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()
                }
            }
        }
        
        stage('Build') {
            steps {
                echo "Building application with Maven..."
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo "Running unit tests..."
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    // Publish JaCoCo coverage report
                    publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')], 
                                   sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
                }
            }
        }
        
        stage('Code Quality') {
            parallel {
                stage('SpotBugs Analysis') {
                    steps {
                        echo "Running SpotBugs static analysis..."
                        sh 'mvn spotbugs:check'
                    }
                    post {
                        always {
                            publishHTML([
                                allowMissing: false,
                                alwaysLinkToLastBuild: true,
                                keepAll: true,
                                reportDir: 'target/spotbugsXml',
                                reportFiles: 'spotbugsXml.xml',
                                reportName: 'SpotBugs Report'
                            ])
                        }
                    }
                }
                
                stage('Dependency Check') {
                    steps {
                        echo "Checking for security vulnerabilities..."
                        sh 'mvn org.owasp:dependency-check-maven:check'
                    }
                    post {
                        always {
                            publishHTML([
                                allowMissing: false,
                                alwaysLinkToLastBuild: true,
                                keepAll: true,
                                reportDir: 'target',
                                reportFiles: 'dependency-check-report.html',
                                reportName: 'Dependency Check Report'
                            ])
                        }
                    }
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                echo "Starting Oracle database for integration tests..."
                sh '''
                    docker-compose up -d oracle-db
                    sleep 60  # Wait for database to be ready
                '''
                
                echo "Running integration tests..."
                sh 'mvn verify -Dspring.profiles.active=test'
            }
            post {
                always {
                    echo "Stopping Oracle database..."
                    sh 'docker-compose down'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo "Creating application package..."
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        
        stage('Docker Build') {
            steps {
                echo "Building Docker image..."
                script {
                    def image = docker.build("${DOCKER_IMAGE}")
                    docker.withRegistry("https://${DOCKER_REGISTRY}", 'docker-registry-credentials') {
                        image.push("${APP_VERSION}")
                        image.push("latest")
                    }
                }
            }
        }
        
        stage('Security Scan') {
            steps {
                echo "Running Trivy security scan..."
                sh '''
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                        -v $(pwd):/workspace \
                        aquasec/trivy image --exit-code 0 --severity HIGH,CRITICAL \
                        --format table ${DOCKER_IMAGE}
                '''
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                echo "Deploying to staging environment..."
                sh '''
                    docker-compose -f docker-compose.staging.yml down
                    docker-compose -f docker-compose.staging.yml pull
                    docker-compose -f docker-compose.staging.yml up -d
                '''
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploying to production environment..."
                script {
                    def userInput = input(
                        id: 'userInput',
                        message: 'Deploy to production?',
                        parameters: [
                            choice(
                                choices: ['Deploy', 'Abort'],
                                description: 'Choose deployment action',
                                name: 'DEPLOY_ACTION'
                            )
                        ]
                    )
                    
                    if (userInput == 'Deploy') {
                        sh '''
                            docker-compose -f docker-compose.prod.yml down
                            docker-compose -f docker-compose.prod.yml pull
                            docker-compose -f docker-compose.prod.yml up -d
                        '''
                    } else {
                        error "Deployment aborted by user"
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo "Pipeline execution completed"
            cleanWs()
        }
        success {
            echo "Pipeline succeeded!"
            // Send success notification (e.g., Slack, email)
        }
        failure {
            echo "Pipeline failed!"
            // Send failure notification (e.g., Slack, email)
        }
        unstable {
            echo "Pipeline unstable!"
        }
    }
}
