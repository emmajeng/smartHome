// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: strings.proto

package io.grpc.examples.strings;

public final class StringsServiceImpl {
  private StringsServiceImpl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_strings_LengthResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_strings_LengthResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_strings_RedactRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_strings_RedactRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_strings_StringRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_strings_StringRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_strings_StringResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_strings_StringResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rstrings.proto\022\007strings\" \n\016LengthRespon" +
      "se\022\016\n\006length\030\001 \001(\005\"*\n\rRedactRequest\022\013\n\003v" +
      "al\030\001 \001(\t\022\014\n\004char\030\002 \001(\t\"\034\n\rStringRequest\022" +
      "\013\n\003val\030\001 \001(\t\"\035\n\016StringResponse\022\013\n\003val\030\001 " +
      "\001(\t2\222\002\n\016StringsService\022<\n\007reverse\022\026.stri" +
      "ngs.StringRequest\032\027.strings.StringRespon" +
      "se\"\000\022=\n\010blackOut\022\026.strings.RedactRequest" +
      "\032\027.strings.StringResponse\"\000\022F\n\rreverseSt" +
      "ream\022\026.strings.StringRequest\032\027.strings.S" +
      "tringResponse\"\000(\0010\001\022;\n\006length\022\026.strings." +
      "StringRequest\032\027.strings.LengthResponse(\001" +
      "B6\n\030io.grpc.examples.stringsB\022StringsSer" +
      "viceImplP\001\242\002\003SSIb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_strings_LengthResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_strings_LengthResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_strings_LengthResponse_descriptor,
        new java.lang.String[] { "Length", });
    internal_static_strings_RedactRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_strings_RedactRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_strings_RedactRequest_descriptor,
        new java.lang.String[] { "Val", "Char", });
    internal_static_strings_StringRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_strings_StringRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_strings_StringRequest_descriptor,
        new java.lang.String[] { "Val", });
    internal_static_strings_StringResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_strings_StringResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_strings_StringResponse_descriptor,
        new java.lang.String[] { "Val", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
