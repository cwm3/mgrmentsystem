<template>
    <div>

        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input placeholder="请输入菜单名称进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                          clearable
                          @clear="initMenus"
                          style="width: 350px;margin-right: 10px" v-model="keyword"
                          @keydown.enter.native="initMenus" :disabled="showAdvanceSearchView"></el-input>
                <el-button icon="el-icon-search" type="primary" @click="initMenus" :disabled="showAdvanceSearchView">
                    搜索
                </el-button>

            </div>
            <div>
                <el-button type="primary" icon="el-icon-plus" @click="showAddDeptView">
                    添加菜单
                </el-button>
            </div>
        </div>

        <div style="margin-top: 10px">
            <el-table
                    :data="menus"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    @selection-change="handleSelectionChange"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        type="index"
                        fixed
                        align="left"
                        label="序号"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="url"
                        fixed
                        align="left"
                        label="url"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="path"
                        fixed
                        align="left"
                        label="path"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="component"
                        fixed
                        align="left"
                        label="组件"
                        width="180">
                </el-table-column>
                <el-table-column
                    prop="name"
                    fixed
                    align="left"
                    label="菜单名"
                    width="150">
            </el-table-column>
                <el-table-column
                        prop="iconCls"
                        fixed
                        align="left"
                        label="菜单图标"
                        width="250">
                </el-table-column>

                <el-table-column
                        prop="parentId"
                        fixed
                        align="left"
                        label="上级菜单"
                        width="150">
                </el-table-column>
<!--                <el-table-column-->
<!--                        prop="requireAuth"-->
<!--                        fixed-->
<!--                        align="left"-->
<!--                        label="菜单权限"-->
<!--                       >-->
<!--                    <template slot-scope="scope">-->
<!--                        <el-button v-if="scope.row.requireAuth==1" style="padding: 3px" size="small" >是-->
<!--                        </el-button>-->
<!--                        <el-button v-if="scope.row.requireAuth==0" style="padding: 3px" size="small" >否-->
<!--                        </el-button>-->
<!--                    </template>-->
<!--                </el-table-column>-->

                <el-table-column
                        fixed="right"

                        label="操作">
                    <template slot-scope="scope" style="align-content: center">
                        <el-button @click="showEditDeptView(scope.row)" style="padding: 3px" size="small">编辑</el-button>
                        <el-button @click="deleteDept(scope.row)" style="padding: 3px" size="small" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="40%">
            <div>
                <el-form  :label-position="labelPosition" label-width="100px" :model="ruleFrom" :rules="rules" ref="menuFrom">
                    <el-form-item  label="url:" prop="url" style="align-content: center">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit"
                                  v-model="ruleFrom.url"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="路径:" prop="path">
                        <el-input size="small" style="width: 400px" prefix-icon="el-icon-edit"
                                  v-model="ruleFrom.path"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="组件:" prop="component">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit" v-model="ruleFrom.component"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="菜单名:" prop="name">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit"
                                  v-model="ruleFrom.name"
                        ></el-input>

                    </el-form-item>
                    <el-form-item label="菜单图标:" prop="iconCls">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit" v-model="ruleFrom.iconCls"
                        ></el-input>
                    </el-form-item>

                    <el-form-item label="上级菜单:" prop="parentId">
                        <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="pname"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click=" editDept">确 定</el-button>
            </span>
        </el-dialog>


    </div>
</template>


<script>
    export default {
        name: "OrganDept",
        data() {
            return {
                // searchValue: {
                //     politicId: null,
                //     nationId: null,
                //     jobLevelId: null,
                //     posId: null,
                //     engageForm: null,
                //     departmentId: null,
                //     beginDateScope: null
                // },
                title: '',
                id:'',
                showAdvanceSearchView: false,
                allDeps: [],
                menus: [],
                loading: false,
                labelPosition: 'right',
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                multipleSelection: [],
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
                filterText: '',
                menu: {
                    component: null,
                    enabled: null,
                    iconCls: null,
                    id: null,
                    keepAlive: null,
                    requireAuth:null,
                    name: null,
                    parentId: null,
                    path: null,
                    roles: null,
                    url: null,
                    pname:null
                },
                ruleFrom:{
                    component: null,
                    enabled: null,
                    iconCls: null,
                    id: null,
                    keepAlive: null,
                    requireAuth:null,
                    name: null,
                    parentId: null,
                    path: null,
                    roles: null,
                    url: null

                },
                pname:"",

                rules: {

                }
            }
        },
        mounted() {
            this.initMenus();
        },
        methods: {
            handleSelectionChange(val) {
                console.log(val)
                this.multipleSelection = val;
            },
            searvhViewHandleNodeClick(data) {
                this.inputDepName = data.name;
                this.searchValue.departmentId = data.id;
                this.popVisible2 = !this.popVisible2
            },
            showAddDeptView() {
                this.title = '添加菜单';
                this.dialogVisible = true;
            },
            showEditDeptView(data) {
                this.title = '编辑菜单信息';
                console.log(data)
                this.selectPreant(data.parentId);
                this.ruleFrom = data;
                this.dialogVisible = true;
            },
            selectPreant(val){
                this.loading = true;
                let url = '/mgrsystem/config/getMenuByParentId?id=' + val;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.pname = resp.obj.name;
                    }
                });

            },
            deleteDept(data) {
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/mgrsystem/config/delete?id=" + data.id).then(resp => {
                        if (resp) {
                            this.initMenus();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            editDept() {
                if (this.ruleFrom.id) {
                    this.$refs['ruleFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/config/updateMenu", this.ruleFrom).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initMenus();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['ruleFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/basic/config/addMenu/", this.ruleFrom).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initMenus();
                                }
                            })
                        }
                    });
                }
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initMenus();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initMenus();
            },
            // showAddDeptView() {
            //     this.emptyEmp();
            //     this.title = '添加部门';
            //     this.getMaxWordID();
            //     this.dialogVisible = true;
            // },
            initMenus(type) {
                this.loading = true;
                let url = '/mgrsystem/config/menuList' + '?pageNum='+ this.page + '&pageSize=' + this.size;
                url += "&name=" + this.keyword;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.menus = resp.obj.records;
                        this.total = resp.obj.total;
                    }
                });
            },

        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>
