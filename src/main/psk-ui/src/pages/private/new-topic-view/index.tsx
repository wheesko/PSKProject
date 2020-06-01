import React, { useEffect, useState } from 'react';

import { Button, Card, Checkbox, Col, Form, Input, Row, Select, Spin, Tooltip, Typography } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';
import {
	ADD_A_DESCRIPTION,
	DESCRIPTION,
	INPUT_TOPIC_NAME,
	IS_THIS_A_SUBTOPIC,
	NEW_TOPIC_SUBTITLE,
	NEW_TOPIC_TITLE,
	SUBMIT,
	SUBTOPIC_EXPLAINER,
	TOPIC_NAME
} from '../../../constants/otherConstants';

import './NewTopicStyles.css';
import notificationService, { NotificationType } from '../../../service/notification-service';
import { LearningTopic } from '../../../models/learningTopic';
import topicService from '../../../api/topic-service';
import { TopicCreateRequest } from '../../../api/model/topic-create-request';

const { Title } = Typography;

const formItemLayout = {
	labelCol: {
		xs: { span: 24, gutter: 12 },
		sm: { span: 8 }
	},
	wrapperCol: {
		xs: { span: 24 },
		sm: { span: 8 }
	}
};

const NewTopicView: React.FunctionComponent<{}> = () => {
	const [isSubtopic, setIsSubtopic] = useState<boolean>(false);
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);
	const [selectedTopic, setSelectedTopic] = useState<LearningTopic>();
	const [height, setHeight] = useState<number>(0);

	const onTopicChange = (value: number) => {
		form.setFieldsValue({ topic: value });
		setSelectedTopic(topics[topics.findIndex(topic => topic.id === value)]);
	};

	const handleCheckboxChange = () => {
		if (isSubtopic) setSelectedTopic(undefined);
		setIsSubtopic(!isSubtopic);
	};

	const [form] = Form.useForm();

	useEffect(() => {
		setLoading(true);
		loadTopics().then(topics => {
				setTopics(topics.sort((a, b) => a.name > b.name ? 1 : -1));
				setLoading(false);
			}
		).catch(e => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not get topics'
			});
		});
	}, []);

	function loadTopics(): Promise<LearningTopic[]> {
		return topicService.getAllTopics();
	}

	const subTopicLabel: React.ReactNode = <>
		{IS_THIS_A_SUBTOPIC}
		<Tooltip title={SUBTOPIC_EXPLAINER}>
			<QuestionCircleOutlined style={{ paddingLeft: '4px' }}/>
		</Tooltip>
	</>;

	return <>
		<Title level={1} className={'newTopicTitle'}>{NEW_TOPIC_TITLE}</Title>
		<p className={'newTopicSubtitle'}>{NEW_TOPIC_SUBTITLE}</p>
		<Card className="newTopicCard">
			<Spin spinning={loading}>
				{renderForm()}
			</Spin>
		</Card>
	</>;

	function renderForm(): React.ReactNode {
		return (
			<Form.Provider
				onFormFinish={name => {
					createNewTopic({
						description: form.getFieldsValue()['topicDescription'],
						name: form.getFieldsValue()['topicName'],
						parentTopicId: selectedTopic?.id ? selectedTopic.id : null
					});
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
					<Form.Item
						name="isASubtopic"
						label={subTopicLabel}
						colon={false}
						className="checkbox"
					>
						<Checkbox onChange={handleCheckboxChange}/>
					</Form.Item>
					{isSubtopic
						? <Form.Item
							label={'Parent topic name'}
							name="parentTopic"
							rules={[{ required: true, message: INPUT_TOPIC_NAME }]}
						>
							<Select
								placeholder="Select parent topic"
								className="select-parent-topic"
								disabled={!isSubtopic}
								showSearch
								onChange={onTopicChange}
								filterOption={(input, option) =>
									option?.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
								}
							>
								{topics.map(topic => {
									return <Select.Option key={topic.id} value={topic.id}>
										{topic.name}
									</Select.Option>;
								})}
							</Select>
						</Form.Item>
						: null
					}
					<Typography.Paragraph>
						{selectedTopic !== null && selectedTopic !== undefined && selectedTopic.description
							? <Row>
								<Col xs={24} sm={8}>
									<Typography.Text className="topic-description-text-first">
										Parent topic description:
									</Typography.Text>
								</Col>
								<Col xs={24} sm={16}>
									<Typography.Text className="topic-description-text">
										{topics[topics.indexOf(selectedTopic)]?.description}
									</Typography.Text>
								</Col>
							</Row>
							: null
						}
					</Typography.Paragraph>
					<Form.Item
						label={DESCRIPTION}
						style={{ height: height }}
						name="topicDescription"
						className="topic-description"
					>
						<Input.TextArea
							placeholder={ADD_A_DESCRIPTION}
							allowClear
							onResize={({ width, height }) => setHeight(height)}
						/>
					</Form.Item>
					<Form.Item
						wrapperCol={{
							xs: { span: 24, offset: 0 },
							sm: { span: 16, offset: 8 }
						}}
					>
						<Button
							className="submit-topic-button"
							type="primary"
							htmlType="submit"
						>
							{SUBMIT}
						</Button>
					</Form.Item>
				</Form>
			</Form.Provider>
		);
	}

	function createNewTopic(topicCreateRequest: TopicCreateRequest): Promise<void> {
		setLoading(true);
		return topicService.createNewTopic(topicCreateRequest).then(() => {
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Topic created successfully'
			});
		}).then(() => {
			return loadTopics();
		}).then((learningTopics) => {
			setTopics(learningTopics.sort((a, b) => a.name > b.name ? 1 : -1));
			setSelectedTopic(learningTopics[learningTopics.findIndex(topic =>
				topic.id === topicCreateRequest.parentTopicId)]
			);
			setLoading(false);
			return;
		}).catch((e) => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Topic could not be created',
				description: 'Check if topic already exists'
			});
			setLoading(false);
		});
	}
};

export { NewTopicView };