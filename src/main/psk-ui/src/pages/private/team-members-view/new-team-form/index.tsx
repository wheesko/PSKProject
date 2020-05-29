import React, { useEffect, useState } from 'react';

import { Alert, Button, Form, Input, Select, Spin, Tooltip, } from 'antd';
import {
	CREATE_A_NEW_TEAM,
	CREATE_TEAM,
	CREATE_TEAM_INFO,
	TEAM_GOALS_EXPLAINER,
	TEAM_GOALS_LABEL,
	TEAM_NAME_LABEL,
	TEAM_NAME_REQUIRED,
} from '../../../../constants/employeeConstants';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import { QuestionCircleOutlined } from '@ant-design/icons';
import { LearningTopic } from '../../../../models/learningTopic';
import topicService from '../../../../api/topic-service';
import teamService from '../../../../api/team-service';
import { CreateTeamRequest } from '../../../../api/model/create-team-request';

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

interface NewTeamFormProps {
	id: number;
}

const NewTeamForm: React.FunctionComponent<NewTeamFormProps> = (props: NewTeamFormProps) => {
	const [isSending, setIsSending] = useState<boolean>(false);

	const [form] = Form.useForm();
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);
	const [selectedTopic, setSelectedTopic] = useState<LearningTopic>();

	function loadTopics(): Promise<LearningTopic[]> {
		return topicService.getAllTopics();
	}

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

	const onTopicChange = (value: number) => {
		form.setFieldsValue({ topic: value });
		setSelectedTopic(topics[topics.findIndex(topic => topic.id === value)]);
	};

	const teamGoalsLabel: React.ReactNode = <>
		{TEAM_GOALS_LABEL}
		<Tooltip title={TEAM_GOALS_EXPLAINER}>
			<QuestionCircleOutlined style={{ paddingLeft: '4px' }}/>
		</Tooltip>
	</>;

	function createTeam(createTeamRequest: CreateTeamRequest) {
		setLoading(true);
		return teamService.createTeam(createTeamRequest).then(() => {
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Team created successfully'
			});
			notificationService.notify({
				notificationType: NotificationType.INFO,
				message: 'Sign out to see changes.',
				description: 'Sign out and sign in to see your newly created team.'
			});
		}).catch((e) => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Team could not be created',
				description: 'Team name might be taken'
			});
			setLoading(false);
		});
	}

	return (
		<>
			<Alert
				type="info"
				showIcon
				message={CREATE_A_NEW_TEAM}
				description={
					<>
						<p>{CREATE_TEAM_INFO}</p>
						<span>You will be able to:</span>
						<ul>
							<li>manage their learning day quarter constraint;</li>
							<li>manage their role;</li>
							<li>manage their goals;</li>
							<li>get info about learning days;</li>
						</ul>
					</>
				}
				className="infoAlert"/>
			<Spin spinning={isSending} size="small">
				<Form.Provider onFormFinish={() => createTeam({
					managerId: props.id,
					name: form.getFieldsValue()['teamName'],
					goal: selectedTopic === undefined ? null : selectedTopic.name
				})}>
					<Form
						form={form}
						{...formItemLayout}
						initialValues={{ remember: true }}
						name="newTeamForm"
					>
						<Form.Item
							label={TEAM_NAME_LABEL}
							name="teamName"
							rules={[{ required: true, message: TEAM_NAME_REQUIRED }]}
						>
							<Input allowClear/>
						</Form.Item>
						<Form.Item
							label={teamGoalsLabel}
							name="teamGoals"
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
								{CREATE_TEAM}
							</Button>
						</Form.Item>
					</Form>
				</Form.Provider>
			</Spin>
		</>
	);
}

;

export { NewTeamForm };
