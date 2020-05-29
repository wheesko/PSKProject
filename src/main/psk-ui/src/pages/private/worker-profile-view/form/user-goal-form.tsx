import React, { useEffect, useState } from 'react';

import { Button, Card, Col, Form, Row, Select, Spin, Typography } from 'antd';
import { INPUT_EVENT_NAME } from '../../../../constants/otherConstants';

import { LearningTopic } from '../../../../models/learningTopic';
import topicService from '../../../../api/topic-service';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import { WorkerGoalCreateRequest } from '../../../../api/model/worker-goal-create-request';
import workerService from '../../../../api/worker-service';

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

interface EventFormProps {
    workerId: number;
    onSubmit: () => void;
}

const UserGoalForm: React.FunctionComponent<EventFormProps> = (props: EventFormProps) => {
	const [form] = Form.useForm();
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);
	const [selectedTopic, setSelectedTopic] = useState<LearningTopic>();

	const {
		workerId,
		onSubmit
	} = props;

	const onTopicChange = (value: number) => {
		form.setFieldsValue({ topic: value });
		setSelectedTopic(topics[topics.findIndex(topic => topic.id === value)]);
	};

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
		<Card title={'Assign new goal'}>
			<Spin spinning={loading}>
				<Form.Provider
					onFormFinish={name => {
						createLearningGoal({
							topic: form.getFieldsValue()['learningEventTopic'],
							worker: workerId
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
								Assign goal
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
	
	function createLearningGoal(goal: WorkerGoalCreateRequest): Promise<void> {
	    return workerService.assignGoal(goal)
			.then(() => {
				notificationService.notify({
					notificationType: NotificationType.SUCCESS,
					message: 'Goal created'
				});
				onSubmit();
			})
			.catch(() => {
				notificationService.notify({
					notificationType: NotificationType.ERROR,
					message: 'Could not create goal'
				});
			});
	}
};

export { UserGoalForm };
