import React, { useState } from 'react';

import { Typography, Form, Input, Button, Card, Tooltip, Checkbox, Select, Row, Col } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';
import {
	ADD_A_DESCRIPTION,
	DESCRIPTION,

	INPUT_TOPIC_NAME, IS_THIS_A_SUBTOPIC,
	NEW_TOPIC_SUBTITLE,
	NEW_TOPIC_TITLE, SUBMIT, SUBTOPIC_EXPLAINER, TOPIC_NAME
} from '../../../constants/otherConstants';

import './NewTopicStyles.css';

const { Title } = Typography;
const { Option } = Select;

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

const NewTopicView: React.FunctionComponent<{}> = () => {
	const [isSubtopic, setIsSubtopic] = useState<boolean>(false);

	const handleCheckboxChange = () => {
		setIsSubtopic(!isSubtopic);
	};

	const [form] = Form.useForm();

	return <>
		<Title level={1} className={'newTopicTitle'}>{NEW_TOPIC_TITLE}</Title>
		<p className={'newTopicSubtitle'}>{NEW_TOPIC_SUBTITLE}</p>
		<Card className="newTopicCard">
			<Form.Provider
				onFormFinish={name => {
					console.log(form.getFieldsValue());
					if (name === 'form1') {
						// Do something...
					}
				}}
			>
				<Form
					form={form}
					{...formItemLayout}
					initialValues={{ remember: true }}
					name="newTopicForm"
				>
					<Form.Item
						label={TOPIC_NAME}
						name="topicName"
						rules={[{ required: true, message: INPUT_TOPIC_NAME }]}
					>
						<Input allowClear/>
					</Form.Item>
					<Form.Item name="isASubtopic"
							   label={<>
								   {IS_THIS_A_SUBTOPIC}
								   <Tooltip title={SUBTOPIC_EXPLAINER}>
									   <QuestionCircleOutlined style={{ paddingLeft: '4px' }}/>
								   </Tooltip>
							   </>} colon={false}>
						<Row gutter={[12, 12]} justify="start" align="middle">
							<Col flex={2}><Checkbox onChange={handleCheckboxChange}/></Col>
							<Col flex={3}>
								<Select placeholder="Select parent topic" disabled={!isSubtopic}>
									<Option value="Java">Java</Option>
								</Select>
							</Col>
						</Row>
					</Form.Item>
					<Form.Item label={DESCRIPTION} name="topicDescription">
						<Input.TextArea
							placeholder={ADD_A_DESCRIPTION}
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
							{SUBMIT}
						</Button>
					</Form.Item>
				</Form>
			</Form.Provider>
		</Card>
	</>;
};

export { NewTopicView };