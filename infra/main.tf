terraform {
  cloud {
    organization = "hajali-test"

    workspaces {
      name = "tf-devops-project"
    }
  }
}

provider "aws" {
   region     = var.aws-region
   access_key = var.aws-access-key
   secret_key = var.aws-secret-key
}

# Define security group for the instance
resource "aws_security_group" "image-resizer" {
  name        = "hajali-sec-grp"
  description = "Security group for image-resizer"

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Spring Boot Server"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "hajali-tf-sec-grp"
  }
}

# Define key pairs for the instance
resource "aws_key_pair" "deployer" {
  key_name   = "hajali-ec2-keys"
  public_key = var.ec2-public-key
}

# Define EC2 provision
resource "aws_instance" "hajali-tf-ec2" {
  ami                         = "ami-015c25ad8763b2f11"
  instance_type               = "t2.micro"
  vpc_security_group_ids      = [aws_security_group.image-resizer.id]
  key_name                    = aws_key_pair.deployer.id
  tags = {
    Name = "hajali-amine-tf-ec2"
  }
  user_data = <<-EOF
    #!/bin/bash
    curl -fsSL get.docker.com -o get-docker.sh
    sudo sh get-docker.sh
  EOF

}