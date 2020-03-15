import React, {useState} from 'react';
import {Form, Input, Button, TimePicker, Card} from 'antd';
import * as moment from 'moment';

const {RangePicker} = TimePicker;
const formItemLayout = {
    labelCol: {span: 6},
    wrapperCol: {span: 6},
};
const formTailLayout = {
    labelCol: {span: 4},
    wrapperCol: {span: 8, offset: 4},
};
const layout = {
    labelCol: {span: 10},
    wrapperCol: {span: 10},
};
const EventForm: React.FunctionComponent<{}> = () => {
    return <Card title="Add new learning event"><Form  {...layout}
                                              initialValues={{remember: true}}
                                              name="learning-event-form">
        <Form.Item
            label="Event Name"
            name="learning-event-name"
            rules={[{required: true, message: 'Input event name'}]}
        >
            <Input/>
        </Form.Item>
        <Form.Item name="learning-event-time" label="Choose time">
            <RangePicker
                format="hh:mm" picker={'time'} minuteStep={15}/>
        </Form.Item>
    </Form></Card>
}
export default EventForm;