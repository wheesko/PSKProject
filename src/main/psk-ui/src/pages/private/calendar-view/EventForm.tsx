import React, { useState } from "react";
import { Form, Input, Button, TimePicker, Card, Tooltip } from "antd";
import { CheckOutlined, CloseOutlined } from "@ant-design/icons";
import * as moment from "moment";
import "./EventFormStyles.css";
import {
    ADD_LEARNING_EVENT_COMMENT, ADD_NEW_LEARNING_EVENT,
    CHOOSE_TIME, COMMENT, EVENT_NAME, INPUT_EVENT_NAME,
    SAVE_LEARNING_EVENT,
    WARNING_EVENT_TIME_IS_REQUIRED
} from "../../../Constants";

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
    <Card title={ADD_NEW_LEARNING_EVENT}>
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
            label={EVENT_NAME}
            name="learningEventName"
            rules={[{ required: true, message: INPUT_EVENT_NAME }]}
          >
            <Input allowClear />
          </Form.Item>
          <Form.Item name="learningEventTime" label={CHOOSE_TIME}  rules={[{ required: true, message:  WARNING_EVENT_TIME_IS_REQUIRED }]}>
            <RangePicker format="hh:mm" picker={"time"} minuteStep={15} />
          </Form.Item>
          <Form.Item label={COMMENT} name="learningEventComment">
            <Input.TextArea
              placeholder={ADD_LEARNING_EVENT_COMMENT}
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
              {SAVE_LEARNING_EVENT}
            </Button>
          </Form.Item>
        </Form>
      </Form.Provider>
    </Card>
  );
};
export default EventForm;
