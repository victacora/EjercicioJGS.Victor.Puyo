pipeline 
{
	//Donde se va a ejecutar el Pipeline
	agent {
		label 'Slave_Induccion'
	}
	//Opciones especificas de Pipeline dentro del Pipeline
	options {
		//Mantener artefactos y salida de consola para el # específico de ejecuciones recientes del Pipeline.
		buildDiscarder(logRotator(numToKeepStr: '3'))
		//No permitir ejecuciones concurrentes de Pipeline
		disableConcurrentBuilds()
	 }
	//Una seccion que define las herramientas para autoinstalar y poner en la PATH
	tools {
		//Preinstalada en la Configuracion del Master
		jdk 'JDK8_Centos' 
		//Preinstalada en la Configuracion del Master
		gradle 'Gradle4.5_Centos'
	}
	//Aqui� comienzan los items del Pipeline
	stages{
		stage('Checkout') {
			steps{
				echo "------------>Checkout<------------"
				checkout([$class: 'GitSCM', branches: [[name: '*/master']],
				doGenerateSubmoduleConfigurations: false, extensions: [], gitTool:
				'Git_Centos', submoduleCfg: [], userRemoteConfigs: [[credentialsId:
				'GitHub_victacora', url:
				'https://github.com/victacora/EjercicioJGS.Victor.Puyo.git']]])
				sh 'gradle --b ./build.gradle compileJava'
			}
		}
		stage('Unit Tests') {
			steps{
				echo "------------>Unit Tests<------------"
				sh 'gradle --b ./build.gradle cleanTest test'
			}
		}
		stage('Integration Tests') {
			steps {
				echo "------------>Integration Tests<------------"
			}
		}
		stage('Static Code Analysis') {
			steps{
				echo '------------>Analisis de codigo estatico<------------'
				withSonarQubeEnv('Sonar') {
					sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
				}
			}
		}
		stage('Build') {
			steps {
				echo "------------>Build<------------"
				sh 'gradle --b ./build.gradle build -x test'
			}
		}
	}
	post {
		always {
			echo 'This will always run'
		}
		success {
			echo 'This will run only if successful'
			junit '**/jacoco/test-results/*.xml'
		}
		failure {
			echo 'This will run only if failed'
			mail (to: 'victor.puyo@ceiba.com.co',subject: "Failed Pipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")
			
		}
		unstable {
			echo 'This will run only if the run was marked as unstable'
		}
		changed {
			echo 'This will run only if the state of the Pipeline has changed'
			echo 'For example, if the Pipeline was previously failing but is now successful'
		}
	}
}