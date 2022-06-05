import os
import shutil
import requests
import boto.s3.connection as boto3


UPLOAD_IMAGE_URL: str = "http://localhost:8080/uploadImage"
GET_IMAGE_URL: str = "http://localhost:8080/getImage/VouFTzNOY50PBchzObCp.jpg"
FILEPATH: str = "e2e/VouFTzNOY50PBchzObCp.jpg"
FILENAME: str = "VouFTzNOY50PBchzObCp.jpg"
TEMP_FILEPATH: str = "e2e/temp.jpg"
BUCKET_NAME: str = "hajali-bucket-for-devops"

def set_up():
    return boto3.S3Connection(os.getenv('AWS_ACCESS_KEY'), os.getenv('AWS_SECRET_KEY'))

# Send POST request to add image
def add_image_to_s3():
    image = open(FILEPATH, "rb")
    files = {
        'image': (image),
        'Content-Type': 'image/jpg',
    }
    return requests.post(UPLOAD_IMAGE_URL, files=files)

# Send GET request to get the image
def get_image_from_s3():
    return requests.get(GET_IMAGE_URL, stream=True)

# Copy response image to folder
def save_image_from_response(response):
    with open(TEMP_FILEPATH, 'wb') as out_file:
        shutil.copyfileobj(response.raw, out_file)
    del response

# Remove object from S3
def clean_up(s3):
    os.remove(TEMP_FILEPATH)
    b = boto3.Bucket(s3, BUCKET_NAME)
    k = boto3.Key(b)
    k.key = FILENAME
    b.delete_key(k)

if __name__ == "__main__":
    print("Set up connection to S...")
    s3_connection = set_up()

    print("Send image...")
    # Call POST /uploadImage
    response = add_image_to_s3()
    # Assert that all good
    assert response.status_code == 200
    assert response.content == b'jawek behi'

    print("Retrieve image...")
    response = get_image_from_s3()
    # Assert that all good
    assert response.status_code == 200

    # Copy response image to folder
    save_image_from_response(response)

    # Read both images
    imageResponse = open("e2e/temp.jpg", "rb").read()
    realImage = image = open(FILEPATH, "rb").read()

    # Assert that the image is loaded perfectly
    assert len(imageResponse) > 0
    # Assert that the image was resized
    assert len(realImage) > len(imageResponse)

    print("Clean up...")
    # Remove temp file and S3 object
    clean_up(s3=s3_connection)

    print("All done!")
