import React, { useEffect, useState } from 'react';

import { Button, Card, Col, DatePicker, Form, Input, Row, Select, Spin, Typography } from 'antd';
import {
	ADD_LEARNING_EVENT_COMMENT,
	COMMENT,
	EVENT_NAME,
	INPUT_EVENT_NAME,
} from '../../../../constants/otherConstants';

import { LearningTopic } from '../../../../models/learningTopic';
import topicService from '../../../../api/topic-service';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import learningDayService from '../../../../api/learning-day-service';
import { LearningEvent } from '../../../../models/learningEvent';
import moment from 'moment';
import { LearningDayUpdateRequest } from '../../../../api/model/learning-day-update-request';

import './EventFormStyles.css';
import { DeleteOutlined } from '@ant-design/icons/lib';

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

interface EditEventFormProps {
    onCreateDay: () => void;
    initialValues?: LearningEvent;
}

const EditEventForm: React.FunctionComponent<EditEventFormProps> = (props: EditEventFormProps) => {
	const [form] = Form.useForm();
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);
	const [isDeleteConfirmVisible, setDeleteConfirmVisible] = useState<boolean>(false);
	const [selectedTopic, setSelectedTopic] = useState<LearningTopic>();

	const onTopicChange = (value: number) => {
		form.setFieldsValue({ topic: value });
		setSelectedTopic(topics[topics.findIndex(topic => topic.id === value)]);
	};

	const {
		onCreateDay,
		initialValues
	} = props;

	useEffect(() => {
		setLoading(true);
		loadTopics().then(topics => {
			setTopics(topics);
			setLoading(false);
			form.setFieldsValue({
				learningEventTopic: initialValues?.topic.id,
				learningEventName: initialValues?.name,
				learningEventComment: initialValues?.comment,
				learningEventDate: moment(initialValues?.dateTimeAt)
			});
			setSelectedTopic(topics[topics.findIndex(topic => topic.id === initialValues?.topic.id)]); 
		}
		).catch(e => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not get topics'
			});
		});
	}, []);

	return (
		<Card title={'Edit this event'}>
			<Spin spinning={loading}>
				<Form.Provider
					onFormFinish={() => {
						update({
							id: initialValues?.id ? initialValues.id : 0,
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
						<Form.Item label={COMMENT} name="learningEventComment">
							<Input.TextArea
								placeholder={ADD_LEARNING_EVENT_COMMENT}
								allowClear
								autoSize={{ minRows: 2, maxRows: 2 }}
							/>
						</Form.Item>
						{isDeleteConfirmVisible &&
							<Typography.Text className="confirm-delete-text">
								Are you sure? Your action cannot be undone
							</Typography.Text>
						}
						<Form.Item
							wrapperCol={{
								xs: { span: 24, offset: 0 },
								sm: { span: 16, offset: 8 }
							}}
							className="edit-form-buttons"
						>
							{isDeleteConfirmVisible
								? <>
									<Button
										onClick={deleteLearningDay}
										className="event-form-buttons success"
										type="primary"
										danger
									>
										Yes
									</Button>
									<Button
										onClick={hideDeleteConfirm}
										className="event-form-buttons success"
									>
										No
									</Button>
								</>
								:<>
									<Button
										className="event-form-buttons success"
										type="primary"
										htmlType="submit"
									>
										Save event
									</Button>
									<Button
										className="event-form-buttons success"
										danger
										type="primary"
										onClick={handleClickDelete}
									>
										<DeleteOutlined/> Delete event
									</Button>
								</>
							}
						</Form.Item>
					</Form>
				</Form.Provider>
			</Spin>
		</Card>
	);

	function handleClickDelete() {
		setDeleteConfirmVisible(true);
	}

	function hideDeleteConfirm() {
		setDeleteConfirmVisible(false);
	}

	function loadTopics(): Promise<LearningTopic[]> {
		return topicService.getAllTopics();
	}

	function deleteLearningDay(): Promise<void> {
		setLoading(true);
		return learningDayService.deleteLearningDay(initialValues?.id ? initialValues.id : 0).then(() => {
			setLoading(false);
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Deleted Learning Day successfully'
			});
			onCreateDay();
		}).catch(e => {
			setLoading(false);
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not delete learning day',
				description: e.response.data.message
			});
		});
	}

	function update(dayRequest: LearningDayUpdateRequest): Promise<void> {
		setLoading(true);
		return learningDayService.updateLearningDay(dayRequest).then(() => {
			setLoading(false);
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Updated Learning Day successfully'
			});
			onCreateDay();
		}).catch(e => {
			setLoading(false);
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not update learning day',
				description: e.response.data.message
			});
		});
	}
};

export { EditEventForm };
