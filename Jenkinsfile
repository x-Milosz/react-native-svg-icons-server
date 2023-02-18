piplien {
	agent {
		docker {
			image: 'gradle'
		}
	}
	stages {
		stage('Build') {
			steps {
				sh 'gradle clean package'
			}
		}
	}
}
