<template>
  <div id="manageQuestionView">
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
      :column-resizable="true"
    >
      <template #judgeConfig="{ record }">
        <json-viewer
          :value="record.judgeConfig"
          :expand-depth="5"
          copyable
          boxed
          sort
        ></json-viewer>
      </template>

      <!--      <template #answer="{ record }">-->
      <!--        <json-viewer-->
      <!--          :value="record.answer"-->
      <!--          :expand-depth="5"-->
      <!--          copyable-->
      <!--          boxed-->
      <!--          sort-->
      <!--        ></json-viewer>-->
      <!--      </template>-->

      <template #tags="{ record }">
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of JSON.parse(record.tags as any)"
            :key="index"
            color="green"
            >{{ tag }}</a-tag
          >
        </a-space>
      </template>

      <template #judgeCase="{ record }">
        <json-viewer
          :value="record.judgeCase"
          :expand-depth="5"
          copyable
          boxed
          sort
        ></json-viewer>
      </template>

      <template #createTime="{ record }">
        {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm") }}
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button type="primary" @click="doUpdate(record)"> 修改</a-button>
          <a-button status="danger" @click="doDelete(record)">删除</a-button>
        </a-space>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref, watchEffect } from "vue";
import { Question, QuestionControllerService } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";

const tableRef = ref();

const dataList = ref([]);
const total = ref(0);
const searchParams = ref({
  pageSize: 10,
  current: 1,
});

const loadData = async () => {
  const res = await QuestionControllerService.listQuestionByPageUsingPost(
    searchParams.value
  );
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
    title: "id",
    dataIndex: "id",
    width: "5%",
    ellipsis: true,
    tooltip: true,
  },
  {
    title: "标题",
    dataIndex: "title",
    width: "5%",
  },
  {
    title: "内容",
    dataIndex: "content",
    width: "10%",
    ellipsis: true,
    tooltip: true,
  },
  {
    title: "标签",
    dataIndex: "tags",
    slotName: "tags",
    width: "5%",
  },
  {
    title: "答案",
    dataIndex: "answer",
    slotName: "answer",
    ellipsis: true,
    tooltip: true,
    width: "10%",
  },
  {
    title: "提交数",
    dataIndex: "submitNum",
    width: 100,
  },
  {
    title: "通过数",
    dataIndex: "acceptedNum",
    width: 100,
  },
  {
    title: "判题配置",
    dataIndex: "judgeConfig",
    slotName: "judgeConfig",
    width: "5%",
  },
  {
    title: "判题用例",
    dataIndex: "judgeCase",
    slotName: "judgeCase",
    width: "5%",
  },
  {
    title: "用户id",
    dataIndex: "userId",
    width: "1%",
    ellipsis: true,
    tooltip: true,
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slotName: "createTime",
    width: "5%",
  },
  {
    title: "操作",
    slotName: "optional",
    width: "20%",
  },
];

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

const doDelete = async (question: Question) => {
  const res = await QuestionControllerService.deleteQuestionUsingPost({
    id: question.id,
  });
  if (res.code === 0) {
    message.success("删除成功");
    loadData();
  } else {
    message.error("删除失败");
  }
};

const router = useRouter();

const doUpdate = (question: Question) => {
  router.push({
    path: "/update/question",
    query: {
      id: question.id,
    },
  });
};
</script>

<style scoped>
#manageQuestionView {
}
</style>
