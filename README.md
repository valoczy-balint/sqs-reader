# SQS reader

This little app connects to an SQS queue, reads all the messages from it and maps them to one selected attribute.
Say you have the following messages in the queue:
```json
[
  {
    "test": "test value"
  },
  {
    "test": "test value 2"
  }
]
```
You would want the values of the 'test' attribute. After running this app, you will find the following result in a result.txt next to the app:
```
[test value2, test value]
```

IMPORTANT:
Make sure the message visibility is high enough on the queue as this tool doesn't remove the messages. 
If the visibility is low, or the number of messages is too high, the messages will reappear in the queue before the app stops running and it will get into an infinite loop.

# Prerequisites
* Java 16

# Setup

* Create an IAM user with SQS policies
* Set up env variables
```shell
export AWS_ACCESS_KEY_ID=your_access_key_id
export AWS_SECRET_ACCESS_KEY=your_secret_access_key
export AWS_REGION=your_aws_region
```

# Run
* Run the following command:
```shell
./gradlew run --args="https://sqs.<region>.amazonaws.com/<account_id>/Test test"
```
*The first argument is the queue url, the second is the attribute you want to map to.