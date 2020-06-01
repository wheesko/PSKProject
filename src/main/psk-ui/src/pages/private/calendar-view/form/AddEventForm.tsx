import React, { useEffect, useState } from 'react';

import { Button, Card, DatePicker, Form, Input, Select, Spin, Typography, Row, Col } from 'antd';
import {
	ADD_LEARNING_EVENT_COMMENT,
	ADD_NEW_LEARNING_EVENT,
	COMMENT,
	EVENT_NAME,
	INPUT_EVENT_NAME,
	SAVE_LEARNING_EVENT
} from '../../../../constants/otherConstants';

import { LearningTopic } from '../../../../models/learningTopic';
import topicService from '../../../../api/topic-service';
import notificationService, { NotificationType } from '../../../../service/notification-service';

import './EventFormStyles.css';
import { LearningDayCreateRequest } from '../../../../api/model/learning-day-create-request';
import learningDayService from '../../../../api/learning-day-service';

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

interface NewEventFormProps {
    onCreateDay: () => void;
}

const NewEventForm: React.FunctionComponent<NewEventFormProps> = (props: NewEventFormProps) => {
	const [form] = Form.useForm();
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);
	const [selectedTopic, setSelectedTopic] = useState<LearningTopic>();

	const onTopicChange = (value: number) => {
		form.setFieldsValue({ topic: value });
		setSelectedTopic(topics[topics.findIndex(topic => topic.id === value)]);
	};
	
	const {
	    onCreateDay
	} = props;

	useEffect(() => {
		setLoading(true);
		loadTopics().then(topics => {
			setTopics(topics);
			setLoading(false);
		}
		).catch(e => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not get topics'
			});
		});
	}, []);

	return (
		<Card>
			<Spin spinning={loading}>
				<Form.Provider
					onFormFinish={() => {
						createLearningDay({
							dateTimeAt: form.getFieldsValue()['learningEventDate'].format('yyyy-MM-DD HH:mm:ss'),
							comment: form.getFieldsValue()['learningEventComment'],
							topic: form.getFieldsValue()['learningEventTopic'],
							name: form.getFieldsValue()['learningEventName']
						});
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
						<Form.Item
							label={'Topic'}
							name="learningEventTopic"
							rules={[{ required: true, message: INPUT_EVENT_NAME }]}
						>
							<Select
								onChange={onTopicChange}
								showSearch
								filterOption={(input, option) =>
									option?.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
								}
							>
								{topics.map(topic => {
									return <Select.Option key={topic.id} value={topic.id}>{topic.name}</Select.Option>;
								})}
							</Select>
						</Form.Item>
						<Typography.Paragraph>
							{selectedTopic !== null && selectedTopic !== undefined && selectedTopic.description
								? <Row>
									<Col xs={24} sm={8}>
										<Typography.Text className="topic-description-text-first">
											Topic description:
										</Typography.Text>
									</Col>
									<Col xs={24} sm={16}>
										<Typography.Text className="topic-description-text">
											{topics[topics.indexOf(selectedTopic)].description}
										</Typography.Text>
									</Col>
								</Row>
								: null
							}
						</Typography.Paragraph>
						<Form.Item label={'Date of event'} name="learningEventDate">
							<DatePicker
								placeholder={'Select date'}
								allowClear
							/>
						</Form.Item>
						<Form.Item label={COMMENT} name="learningEventComment" >
							<Input.TextArea
								placeholder={ADD_LEARNING_EVENT_COMMENT}
								allowClear
								autoSize={{ minRows: 2, maxRows: 2 }}
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
			</Spin>
		</Card>
	);

	function loadTopics(): Promise<LearningTopic[]> {
		return topicService.getAllTopics();
	}

	function createLearningDay(dayRequest: LearningDayCreateRequest): Promise<void> {
		setLoading(true);
		return learningDayService.createLearningDay(dayRequest).then(() => {
			setLoading(false);
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Created Learning Day successfully'
			});
			onCreateDay();
		}).catch(e => {
			setLoading(false);
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not create learning day',
				description: e.response.data.message
			});
		});
	}
};

export { NewEventForm };
