<template>
  <div id="questionsView">
    <a-form :model="searchParams" layout="inline">
      <a-form-item field="questionId" label="题号" style="min-width: 240px">
        <a-input v-model="searchParams.questionId" placeholder="请输入" />
      </a-form-item>
      <a-form-item field="language" label="编程语言" style="min-width: 240px">
        <a-select
          v-model="searchParams.language"
          :style="{ width: '320px' }"
          placeholder="选择编程语言"
        >
          <a-option>java</a-option>
          <a-option>cpp</a-option>
          <a-option>go</a-option>
          <a-option>html</a-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSubmit">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-divider size="0" />
    <a-table
      :ref="tableRef"
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <template #title="{ record }">
        <a
          style="cursor: pointer; color: dodgerblue"
          @click="toQuestionPage(record)"
        >
          {{ record.title }}
        </a>
      </template>
      <template #judgeInfo="{ record }">
        <div v-if="record.judgeInfo.time">
          {{ record.judgeInfo.time + " ms" }}
        </div>
        <div v-else>N/A</div>
      </template>
      <template #createTime="{ record }">
        {{ moment(record.createTime).format("YYYY-MM-DD") }}
      </template>
      <template #status="{ record }">
        <a-space wrap>
          <!--  判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）-->
          <a-tag v-if="record.status === 0" color="gray"> UnJudged </a-tag>
          <a-tag v-if="record.status === 1" color="blue">
            <icon-sync :style="{ fontSize: '15px' }" spin />
            Pending
          </a-tag>
          <a-tag v-if="record.status === 2" color="green"> Accept </a-tag>
          <a-tag v-if="record.status === 3" color="red">
            {{ record.judgeInfo.message }}
          </a-tag>
        </a-space>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import {
  Question,
  QuestionControllerService,
  QuestionSubmitQueryRequest,
  QuestionVO,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import moment from "moment";

const tableRef = ref();

const dataList = ref([]);
const total = ref(0);
const searchParams = ref<QuestionSubmitQueryRequest>({
  questionId: undefined,
  language: undefined,
  pageSize: 8,
  current: 1,
});

const loadData = async () => {
  const res =
    await QuestionControllerService.listQuestionSubmitVoByPageUsingPost({
      ...searchParams.value,
      sortField: "createTime",
      sortOrder: "descend",
    });
  if (res.code === 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  } else {
    message.error("加载失败，" + res.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发页面的重新加载
 */
watchEffect(() => {
  loadData();
});

/**
 * 页面加载时，请求数据
 */
onMounted(() => {
  loadData();
});

const columns = [
  {
    title: "题号",
    dataIndex: "questionId",
    width: "20%",
    ellipsis: true,
    tooltip: true,
  },
  {
    title: "题目名称",
    slotName: "title",
  },
  {
    title: "最新状态",
    slotName: "status",
  },
  {
    title: "运行时间",
    slotName: "judgeInfo",
  },
  {
    title: "编程语言",
    dataIndex: "language",
  },
  {
    title: "提交时间",
    slotName: "createTime",
  },
];

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

const router = useRouter();

/**
 * 跳转到做题页面
 * @param questionVO
 */
const toQuestionPage = (questionVO: QuestionVO) => {
  router.push({
    path: `/view/question/${questionVO.questionId}`,
  });
};

/**
 * 确认搜索，重新加载数据
 */
const doSubmit = () => {
  // 这里需要重置搜索页号
  searchParams.value = {
    ...searchParams.value,
    current: 1,
  };
};
</script>

<style scoped>
#questionsView {
  max-width: 1280px;
  margin: 0 auto;
}
</style>
