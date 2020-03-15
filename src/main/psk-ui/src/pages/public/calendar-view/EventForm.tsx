import React, { useState } from "react";
import { Form, Input, Button, TimePicker, Card, Tooltip } from "antd";
import { CheckOutlined, CloseOutlined } from "@ant-design/icons";
import * as moment from "moment";
import "./EventFormStyles.css";

const { RangePicker } = TimePicker;
const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 8 }
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 16 }
  }
};
const EventForm: React.FunctionComponent<{}> = () => {
  const [form] = Form.useForm();
  return (
    <Card title="Add new learning event">
      <Form.Provider
       onFormFinish={name => {
           console.log(form.getFieldsValue())
        if (name === 'form1') {
          // Do something...
        }
      }}
    >
        <Form
          form={form}
          {...formItemLayout}
          initialValues={{ remember: true }}
          name="learningEventForm"
        >
          <Form.Item
            label="Event Name"
            name="learningEventName"
            rules={[{ required: true, message: "Input event name" }]}
          >
            <Input allowClear />
          </Form.Item>
          <Form.Item name="learningEventTime" label="Choose time"   rules={[{ required: true, message: "Event time is required!" }]}>
            <RangePicker format="hh:mm" picker={"time"} minuteStep={15} />
          </Form.Item>
          <Form.Item label="Comment" name="learningEventComment">
            <Input.TextArea
              placeholder="Add learning event comment"
              allowClear
            />
          </Form.Item>
          <Form.Item
            wrapperCol={{
              xs: { span: 24, offset: 0 },
              sm: { span: 16, offset: 8 }
            }}
          >
            <Button
              className="event-form-buttons success"
              type="primary"
              htmlType="submit"
            >
              Save learning event
            </Button>
          </Form.Item>
        </Form>
      </Form.Provider>
    </Card>
  );
};
export default EventForm;
